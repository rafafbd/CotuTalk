using Microsoft.EntityFrameworkCore;
using BCrypt.Net;
using MailKit.Net.Smtp;
using MailKit.Security;
using MimeKit;
using System.Text.RegularExpressions;
using Microsoft.AspNetCore.Mvc;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseSqlite("Data Source=twtdb.db"));

var app = builder.Build();
app.UseStaticFiles(); 

// Rota raiz
app.MapGet("/", () => "Hello World!");

//IMAGENS
//upload 
app.MapPost("/upload", async (HttpRequest request) =>
{
    var form = await request.ReadFormAsync();
    var file = form.Files["image"];

    if (file == null || file.Length == 0)
        return Results.BadRequest("Nenhuma imagem enviada");

    var uploadsFolder = Path.Combine("wwwroot", "img");

    if (!Directory.Exists(uploadsFolder))
        Directory.CreateDirectory(uploadsFolder);

    var filePath = Path.Combine(uploadsFolder, file.FileName);

    using (var stream = new FileStream(filePath, FileMode.Create))
    {
        await file.CopyToAsync(stream);
    }
    var publicUrl = $"/imgs/{file.FileName}";
    return Results.Ok(new { url = publicUrl });
});

// VERIFICACAO POR EMAIL
//Mandar email de verificacao
app.MapPost("/enviar-email", async (EmailRequest email, AppDbContext db) =>
{
    var mensagem = new MimeMessage();
    
    mensagem.From.Add(new MailboxAddress("Cotutalk Suporte", "cotutalk@gmail.com"));
    mensagem.To.Add(new MailboxAddress("Usuário de Destino", email.email));

    if (!Regex.IsMatch(email.email, @"^cc.{5}@g\.unicamp\.br$"))
        return Results.BadRequest("Email não pertence ao cotuca.");

    var random = new Random();
    var codigo = random.Next(1000000);
    var codigoFormatado = codigo.ToString("D6");
    
    mensagem.Subject = "Confirmação de envio - Cotutalk";
    mensagem.Body = new TextPart("plain") {
        Text = $@"
Olá,

Você solicitou um código de verificação para concluir sua ação no Cotutalk.

Seu código de verificação é:
{codigoFormatado}

Esse código é válido por 10 minutos. Não compartilhe o código com ninguém. Se não foi você quem solicitou esse código, por favor, ignore esta mensagem.

Atenciosamente,
Equipe Cotutalk
        "
    };

    var codigoDeVerificacao =  new CodigoVerificacao {
        Email = email.email, 
        Codigo = codigoFormatado,
        DataDeGeracao = DateTime.Now,
        DataDeExpiracao = DateTime.Now.AddMinutes(10)
    };
    db.CodigosVerificacao.Add(codigoDeVerificacao);
    await db.SaveChangesAsync();

    using var smtp = new SmtpClient();

    try
    {
        await smtp.ConnectAsync("smtp.gmail.com", 587, SecureSocketOptions.StartTls);
        await smtp.AuthenticateAsync("cotutalk@gmail.com", "mbvz mriz skff bioy");
        await smtp.SendAsync(mensagem);
        await smtp.DisconnectAsync(true);

        return Results.Ok("E-mail enviado com sucesso!");
    }
    catch (Exception ex)
    {
        return Results.Problem($"Erro ao enviar e-mail: {ex.Message}");
    }
});

//ver codigos de verificacao enviados
app.MapGet("/codigos-de-verificacao", async (AppDbContext db) => 
{
    var codigos = await db.CodigosVerificacao.ToListAsync();
    return Results.Ok(codigos);
});

//validar codigo
app.MapPost("/validar-codigo", async (ValidarCodigoRequest validacao, AppDbContext db) => 
{
    var codigoVerificacao = await db.CodigosVerificacao.FirstOrDefaultAsync(c => c.Email == validacao.Email && c.Codigo == validacao.CodigoInserido);
    if (codigoVerificacao is null)
        return Results.NotFound("Código inválido ou não encontrado.");
    if (codigoVerificacao.DataDeExpiracao < DateTime.Now)
        return Results.BadRequest("O código expirou.");
    else
        return Results.Ok("Código validado com sucesso!");
});

// USUARIO
// Adicionar um usuario
// app.MapPost("/usuarios", async (Usuario usuario, AppDbContext db) =>
// {
//     usuario.Senha = BCrypt.Net.BCrypt.HashPassword(usuario.Senha);
//     db.Usuarios.Add(usuario);
//     await db.SaveChangesAsync();
//     return Results.Created($"/usuarios/{usuario.IdUsuario}", usuario);
// });

app.MapPost("/usuarios", async (UsuarioDTO dto, AppDbContext db) =>
{
    var usuario = new Usuario
    {
        Nome = dto.Nome,
        Email = dto.Email,
        Senha = BCrypt.Net.BCrypt.HashPassword(dto.Senha),
        Biografia = dto.Biografia,
        ImagePath = dto.ImagePath
    };
    db.Usuarios.Add(usuario);
    await db.SaveChangesAsync();
    return Results.Created($"/usuarios/{usuario.IdUsuario}", usuario);
});

// Listar usuarios
app.MapGet("/usuarios", async (AppDbContext db) =>
{
    var usuarios = await db.Usuarios.ToListAsync();
    return Results.Ok(usuarios);
});

// Buscar usuario por ID
app.MapGet("/usuarios/{id}", async (int id, AppDbContext db) => 
{
    var usuario = await db.Usuarios.FirstOrDefaultAsync(u => u.IdUsuario == id);
    if (usuario is null)
        return Results.NotFound();
    return Results.Ok(usuario);
});

// Deletar um usuario por ID
app.MapDelete("/usuarios/{id}", async (int id, AppDbContext db) =>
{
    var usuario = await db.Usuarios.FindAsync(id);
    if (usuario is null)
    {
        return Results.NotFound();
    }
    db.Usuarios.Remove(usuario);
    await db.SaveChangesAsync();
    return Results.NoContent();
});

// Atualizar um usuario por ID
// app.MapPut("/usuarios/{id}", async (int id, Usuario usuarioAtualizado, AppDbContext db) =>
// {
//     var usuario = await db.Usuarios.FindAsync(id);
//     if (usuario is null)
//     {
//         return Results.NotFound();
//     }

//     usuario.Nome = usuarioAtualizado.Nome;
//     usuario.Email = usuarioAtualizado.Email;
//     if (!string.IsNullOrWhiteSpace(usuarioAtualizado.Senha))
//     {
//         usuario.Senha = BCrypt.Net.BCrypt.HashPassword(usuarioAtualizado.Senha);
//     }

//     await db.SaveChangesAsync();
//     return Results.Ok(usuario);
// });


app.MapPut("/usuarios/{id}", async (int id, UsuarioDTO dto, AppDbContext db) =>
{
    var usuario = await db.Usuarios.FindAsync(id);
    if (usuario is null)
        return Results.NotFound();

    usuario.Nome = dto.Nome;
    usuario.Email = dto.Email;
    if (!string.IsNullOrWhiteSpace(dto.Senha))
        usuario.Senha = BCrypt.Net.BCrypt.HashPassword(dto.Senha);
    usuario.Biografia = dto.Biografia;
    usuario.ImagePath = dto.ImagePath;

    await db.SaveChangesAsync();
    return Results.Ok(usuario);
});


// Verifiar login
// app.MapPost("/login", async (LoginRequest login, AppDbContext db) => {
//     var usuario = await db.Usuarios.FirstOrDefaultAsync(u => u.Email == login.Nome);
//     if (usuario is null)
//         return Results.Unauthorized();
//     bool validacao = BCrypt.Net.BCrypt.Verify(login.Senha, usuario.Senha);
//     return validacao 
//         ? Results.Ok(usuario)
//         : Results.Unauthorized();   
// });

app.MapPost("/login", async (LoginRequest login, AppDbContext db) =>
{
    var usuario = await db.Usuarios
        .FirstOrDefaultAsync(u => u.Email == login.Nome);
    if (usuario is null)
        return Results.Unauthorized();
    bool senhaCorreta = BCrypt.Net.BCrypt.Verify(login.Senha, usuario.Senha);
    if (!senhaCorreta)
        return Results.Unauthorized();

    return senhaCorreta 
        ? Results.Ok(usuario)
        : Results.Unauthorized();
});

// POSTAGEM
// Adicionar um novo post
/*
app.MapPost("/postagens", async (PostagemDTO postagemDTO, AppDbContext db) =>
{
    var grupo = await db.Grupos.FirstOrDefaultAsync(g => g.IdGrupo == postagemDTO.IdGrupo);
    var usuario = await db.Usuarios.FirstOrDefaultAsync(u => u.IdUsuario == postagemDTO.IdUsuario);

    if (grupo is null || usuario is null)
        return Results.BadRequest("Grupo ou Usuário não encontrados.");

    var postagem = new Postagem
    {
        IdUsuario = postagemDTO.IdUsuario,
        IdGrupo = postagemDTO.IdGrupo,
        Conteudo = postagemDTO.Conteudo,
        Grupo = grupo,
        Usuario = usuario
    };

    db.Postagens.Add(postagem);
    await db.SaveChangesAsync();


    // Carregar postagem com navegações preenchidas
    var postagemCriada = await db.Postagens
        .Include(p => p.Usuario)
        .Include(p => p.Grupo)
        .FirstOrDefaultAsync(p => p.IdPostagem == postagem.IdPostagem);

    await db.SaveChangesAsync();

    return Results.Created($"/postagens/{postagemCriada.IdPostagem}", postagemCriada);
});
*/

app.MapPost("/postagens", async (PostagemDTO post, AppDbContext db) =>
{
    var grupo = await db.Grupos.FirstOrDefaultAsync(g => g.IdGrupo == post.IdGrupo);
    var usuario = await db.Usuarios.FirstOrDefaultAsync(u => u.IdUsuario == post.IdUsuario);

    if (grupo is null || usuario is null)
        return Results.BadRequest("Grupo ou Usuário não encontrados.");

    var postagem = new Postagem
    {
        IdUsuario = post.IdUsuario,
        IdGrupo = post.IdGrupo,
        Conteudo = post.Conteudo,
        Grupo = grupo,
        Usuario = usuario
    };

    db.Postagens.Add(postagem);
    await db.SaveChangesAsync();


    // Carregar postagem com navegações preenchidas
    var postagemCriada = await db.Postagens
        .Include(p => p.Usuario)
        .Include(p => p.Grupo)
        .FirstOrDefaultAsync(p => p.IdPostagem == postagem.IdPostagem);

    await db.SaveChangesAsync();

    return Results.Created($"/postagens/{postagemCriada.IdPostagem}", postagemCriada);
});

// Listar posts
app.MapGet("/postagens", async (AppDbContext db) =>
{
    var postagens = await db.Postagens
        .Include(p => p.Usuario)
        .Include(p => p.Grupo)
        .ToListAsync();
    return Results.Ok(postagens);
});

// buscar uma postagem por id
app.MapGet("/postagens/{id}", async (int id, AppDbContext db) =>
{
    var postagem = await db.Postagens.FindAsync(id);

    if (postagem == null)
        return Results.NotFound();

    return Results.Ok(postagem);
});

/*
app.MapGet("/postagensUI", async (AppDbContext db) =>
{
    var postagens = await db.Postagens
        .Include(p => p.Usuario)
        .Include(p => p.Grupo)
        .Include(p => p.Curtidas)
        .Include(p => p.Respostas)
        .Select(p => new PostagemUI
        {
            IdPostagem = p.IdPostagem,
            Conteudo = p.Conteudo,
            Usuario = p.Usuario,
            Grupo = p.Grupo,
            qtsCurtidas = p.Curtidas.Count,
            qtsRespostas = p.Respostas.Count
        })
        .ToListAsync();

    return Results.Ok(postagens);
});

app.MapGet("/postagensUI/{idPostagem}", async (int idPostagem, AppDbContext db) =>
{
    var postagem = await db.Postagens
        .Include(p => p.Usuario)
        .Include(p => p.Grupo)
        .Include(p => p.Curtidas)
        .Include(p => p.Respostas)
        .Where(p => p.IdPostagem == idPostagem)
        .Select(p => new PostagemUI
        {
            IdPostagem = p.IdPostagem,
            Conteudo = p.Conteudo,
            Usuario = p.Usuario,
            Grupo = p.Grupo,
            qtsCurtidas = p.Curtidas.Count,
            qtsRespostas = p.Respostas.Count
        })
        .FirstOrDefaultAsync();

    return postagem is null ? Results.NotFound() : Results.Ok(postagem);
});

app.MapGet("/postagensUI/grupo/{idGrupo:int}", async (int idGrupo, AppDbContext db) =>
{
    var postagens = await db.Postagens
        .Where(p => p.IdGrupo == idGrupo)
        .Include(p => p.Usuario)
        .Include(p => p.Grupo)
        .Include(p => p.Curtidas)
        .Include(p => p.Respostas)
        .Select(p => new PostagemUI
        {
            IdPostagem = p.IdPostagem,
            Conteudo = p.Conteudo,
            Usuario = p.Usuario,
            Grupo = p.Grupo,
            qtsCurtidas = p.Curtidas.Count,
            qtsRespostas = p.Respostas.Count
        })
        .ToListAsync();

    return postagens.Any()
        ? Results.Ok(postagens)
        : Results.NotFound($"Nenhuma postagem encontrada para o grupo {idGrupo}");
});
*/

// Deletar um post por ID
app.MapDelete("/postagens/{id}", async (int id, AppDbContext db) =>
{
    var postagem = await db.Postagens.FindAsync(id);
    if (postagem is null)
    {
        return Results.NotFound();
    }
    db.Postagens.Remove(postagem);
    await db.SaveChangesAsync();
    return Results.NoContent();
});

// Atualizar um post por ID
// app.MapPut("/postagens/{id}", async (int id, Postagem postagemAtualizado, AppDbContext db) =>
// {
//     var postagem = await db.Postagens.FindAsync(id);
//     if (postagem is null)
//     {
//         return Results.NotFound();
//     }
    
//     postagem.Conteudo = postagemAtualizado.Conteudo;

//     await db.SaveChangesAsync();
//     return Results.Ok(postagem);
// });


app.MapPut("/postagens/{id}", async (int id, PostagemDTO dto, AppDbContext db) =>
{
    var postagem = await db.Postagens.FindAsync(id);
    if (postagem is null)
        return Results.NotFound();

    postagem.Conteudo = dto.Conteudo;
    await db.SaveChangesAsync();
    return Results.Ok(postagem);
});


// Posts de usuario
app.MapGet("/postagensUsuario/{IdUsuario:int}", async (int idUsuario, AppDbContext db) => 
{
    var postagens = await db.Postagens
        .Where(p => p.IdUsuario == idUsuario)
        .Include(p => p.Usuario)
        .Include(p => p.Grupo)
        .ToListAsync();

    return postagens.Any()
        ? Results.Ok(postagens)
        : Results.NotFound($"Nenhuma postagem encontrada para o usuário {idUsuario}");
});

// Posts de grupo
app.MapGet("/postagensGrupo/{IdGrupo:int}", async (int idGrupo, AppDbContext db) => 
{
    var postagens = await db.Postagens
                            .Where (p => p.IdGrupo == idGrupo)
                            .ToListAsync();
    return postagens.Any()
        ? Results.Ok(postagens)
        : Results.NotFound($"Nenhuma postagem encontrada para o grupo {idGrupo}");
});

// GRUPO
// Adicionar um grupo
// app.MapPost("/grupos", async (Grupo grupo, AppDbContext db) => 
// {
//     grupo.DataCriacao = DateTime.UtcNow; 
//     db.Grupos.Add(grupo);
//     await db.SaveChangesAsync();
//     return Results.Created($"/grupos/{grupo.IdGrupo}", grupo);
// });


app.MapPost("/grupos", async (GrupoDTO dto, AppDbContext db) =>
{
    var grupo = new Grupo
    {
        Nome = dto.Nome,
        Descricao = dto.Descricao,
        DataCriacao = DateTime.UtcNow
    };

    db.Grupos.Add(grupo);
    await db.SaveChangesAsync();
    return Results.Created($"/grupos/{grupo.IdGrupo}", grupo);
});


// Listar grupos
app.MapGet("/grupos", async (AppDbContext db) => 
{
    var grupos = await db.Grupos.ToListAsync();
    return Results.Ok(grupos);
});

app.MapGet("/grupos/{id}", async (int id, AppDbContext db) => 
{
    var grupo = await db.Grupos.FindAsync(id); 
    if (grupo is null)
    {
        return Results.NotFound(); 
    }
    return Results.Ok(grupo); 
});

// Deletar um grupo por ID
app.MapDelete("/grupos/{id}", async (int id, AppDbContext db) =>
{
    var grupo = await db.Grupos.FindAsync(id);
    if (grupo is null)
    {
        return Results.NotFound();
    }
    db.Grupos.Remove(grupo);
    await db.SaveChangesAsync();
    return Results.NoContent();
});

// Atualizar um grupo por ID
// app.MapPut("/grupos/{id}", async (int id, Grupo grupoAtualizado, AppDbContext db) =>
// {
//     var grupo = await db.Grupos.FindAsync(id);
//     if (grupo is null)
//     {
//         return Results.NotFound();
//     }

//     grupo.Nome = grupoAtualizado.Nome;
//     grupo.Descricao = grupoAtualizado.Descricao;

//     await db.SaveChangesAsync();
//     return Results.Ok(grupo);
// });


app.MapPut("/grupos/{id}", async (int id, GrupoDTO dto, AppDbContext db) =>
{
    var grupo = await db.Grupos.FindAsync(id);
    if (grupo is null)
        return Results.NotFound();

    grupo.Nome = dto.Nome;
    grupo.Descricao = dto.Descricao;
    await db.SaveChangesAsync();
    return Results.Ok(grupo);
});


// Adicionar membro
// app.MapPost("/adicionarMembro", async (Membro membro, AppDbContext db) =>
// {
//     var usuario = await db.Usuarios.FindAsync(membro.IdUsuario);
//     if (usuario == null)
//         return Results.NotFound("Usuário não encontrado.");
//     var grupo = await db.Grupos.FindAsync(membro.Idgrupo);
//     if (grupo == null)
//         return Results.NotFound("Grupo não encontrado.");

//     membro.Usuario = usuario;
//     membro.Grupo = grupo;
//     membro.DataDeEntrada = DateTime.UtcNow;

//     db.Membros.Add(membro);
//     await db.SaveChangesAsync();

//     return Results.Created($"/membros/{membro.IdMembro}", membro);
// });


// todos os membros
app.MapGet("/membros", async (AppDbContext db) =>
{
    var membros = await db.Membros
        .Include(m => m.Usuario)
        .Include(m => m.Grupo)
        .ToListAsync();

    return Results.Ok(membros);
});

app.MapPost("/adicionarMembro", async (MembroDTO dto, AppDbContext db) =>
{
    var usuario = await db.Usuarios.FindAsync(dto.IdUsuario);
    var grupo = await db.Grupos.FindAsync(dto.IdGrupo);

    if (usuario == null || grupo == null)
        return Results.NotFound("Usuário ou grupo não encontrado.");

    var membro = new Membro
    {
        IdUsuario = dto.IdUsuario,
        Idgrupo = dto.IdGrupo,
        Usuario = usuario,
        Grupo = grupo,
        DataDeEntrada = DateTime.UtcNow
    };

    db.Membros.Add(membro);
    await db.SaveChangesAsync();
    return Results.Created($"/membros/{membro.IdMembro}", membro);
});

// todos os grupos de um usuario
app.MapGet("/usuarios/{idUsuario}/grupos", async (int idUsuario, AppDbContext db) =>
{
    var grupos = await db.Membros
        .Where(m => m.IdUsuario == idUsuario)
        .Select(m => m.Grupo)
        .ToListAsync();

    return grupos.Any() ? Results.Ok(grupos) : Results.NotFound("Nenhum grupo encontrado para esse usuário.");
});

// todos os usuarios de um grupo
app.MapGet("/grupos/{idGrupo}/usuarios", async (int idGrupo, AppDbContext db) =>
{
    var usuarios = await db.Membros
        .Where(m => m.Idgrupo == idGrupo)
        .Select(m => m.Usuario)
        .ToListAsync();

    return usuarios.Any() ? Results.Ok(usuarios) : Results.NotFound("Nenhum usuário encontrado nesse grupo.");
});


// Remover membro
app.MapDelete("/deletarMembro", async ([FromBody] Membro membroDeletado, AppDbContext db) => 
{
    var membro = await db.Membros.FirstOrDefaultAsync(m => m.Idgrupo == membroDeletado.Idgrupo && m.IdUsuario == membroDeletado.IdUsuario);
    if (membro is null)
        return Results.NotFound("Membro não encontrado!");
    
    db.Membros.Remove(membro);
    await db.SaveChangesAsync();

    return Results.Ok("Membro deletado com sucesso!"); 
});


// CURTIDA   
// Adicionar curtida
/*
app.MapPost("/curtidas", async (Curtida curtida, AppDbContext db) => 
{
    db.Curtidas.Add(curtida);
    await db.SaveChangesAsync();
    return Results.Created($"/curtidas/{curtida.IdCurtida}", curtida);
});
*/

app.MapPost("/curtidas", async (CurtidaDTO dto, AppDbContext db) =>
{
    var usuario = await db.Usuarios.FindAsync(dto.IdUsuario);
    var postagem = await db.Postagens.FindAsync(dto.IdPostagem);

    if (usuario == null || postagem == null)
        return Results.NotFound("Usuário ou Postagem não encontrados.");

    var curtida = new Curtida
    {
        Usuario = usuario,
        Postagem = postagem
    };

    db.Curtidas.Add(curtida);
    await db.SaveChangesAsync();

    return Results.Created($"/curtidas/{curtida.IdCurtida}", curtida);
});



// Listar curtidas 
app.MapGet("/curtidas", async (AppDbContext db) =>
{
    var curtidas = await db.Curtidas.ToListAsync();
    return Results.Ok(curtidas);
});

// Deletar uma curtida por usuario
app.MapDelete("/curtidas/usuario/{usuario}", async (int usuario, AppDbContext db) =>
{
    var curtida = await db.Curtidas.FirstOrDefaultAsync(c => c.IdUsuario == usuario);
    if (curtida is null)
    {
        return Results.NotFound();
    }
    db.Curtidas.Remove(curtida);
    await db.SaveChangesAsync();
    return Results.NoContent();
});

// Curtidas de um usuario
app.MapGet("/curtidasUsuario/{IdUsuario:int}", async (int idUsuario, AppDbContext db) => 
{
    var curtidas = await db.Curtidas
                            .Where(c => c.IdUsuario == idUsuario)
                            .ToListAsync();
    return curtidas.Any()
        ? Results.Ok(curtidas)
        : Results.NotFound($"Nenhuma curtida encontrada para o usuario {idUsuario}");
});

// Curtidas de um post
app.MapGet("/curtidasPostagem/{idPostagem:int}", async (int idPostagem, AppDbContext db) => 
{
    var curtidas = await db.Curtidas
                            .Where(c => c.IdPostagem == idPostagem)
                            .ToListAsync();
    return curtidas.Any()
        ? Results.Ok(curtidas)
        : Results.NotFound($"Nenhuma curtida encontrada para o post {idPostagem}");
});

// RESPOSTA
// Adicionar resposta
/*
app.MapPost("/respostas", async (Resposta resposta, AppDbContext db) => 
{
    db.Respostas.Add(resposta);
    await db.SaveChangesAsync();
    return Results.Created($"/respostas/{resposta.IdResposta}", resposta);
});
*/

app.MapPost("/respostas", async (RespostaDTO dto, AppDbContext db) =>
{
    var postagem = await db.Postagens.FindAsync(dto.IdPostagem);
    var usuario = await db.Usuarios.FindAsync(dto.IdUsuario);
    if (postagem == null || usuario == null)
        return Results.BadRequest("Postagem ou Usuario não encontrados.");

    var resposta = new Resposta
    {
        IdPostagem = dto.IdPostagem,
        IdUsuario = dto.IdUsuario,
        Conteudo = dto.Conteudo,
        DataComentario = DateTime.UtcNow,
        Usuario = usuario,
        Postagem = postagem
    };
    db.Respostas.Add(resposta);
    await db.SaveChangesAsync();
    return Results.Created($"/respostas/{resposta.IdResposta}", resposta);
});


// Listar respostas
app.MapGet("/respostas", async (AppDbContext db) =>
{
    var respostas = await db.Respostas.ToListAsync();
    return Results.Ok(respostas);
});

// Deletar uma resposta por ID
app.MapDelete("/respostas/{id}", async (int id, AppDbContext db) =>
{
    var resposta = await db.Respostas.FindAsync(id);
    if (resposta is null)
    {
        return Results.NotFound();
    }
    db.Respostas.Remove(resposta);
    await db.SaveChangesAsync();
    return Results.NoContent();
});

// Atualizar uma resposta por ID
app.MapPut("/respostas/{id}", async (int id, Resposta respostaAtualizado, AppDbContext db) =>
{
    var resposta = await db.Respostas.FindAsync(id);
    if (resposta is null)
    {
        return Results.NotFound();
    }
    resposta.Conteudo = respostaAtualizado.Conteudo;
    await db.SaveChangesAsync();
    return Results.Ok(resposta);
});

/*
app.MapPut("/respostas/{id}", async (int id, RespostaDTO dto, AppDbContext db) =>
{
    var resposta = await db.Respostas.FindAsync(id);
    if (resposta is null)
        return Results.NotFound();

    resposta.Conteudo = dto.Conteudo;
    await db.SaveChangesAsync();
    return Results.Ok(resposta);
});
*/

// Respostas de um usuario
app.MapGet("/respostasUsuario/{IdUsuario:int}", async (int idUsuario, AppDbContext db) => 
{
    var respostas = await db.Respostas
                            .Where(c => c.IdUsuario == idUsuario)
                            .ToListAsync();
    var qtsRespostas = respostas.Count();
    return respostas.Any()
        ? Results.Ok(respostas)
        : Results.NotFound($"Nenhuma resposta encontrada para o usuário {idUsuario}");
});

// Respostas de uma postagem
app.MapGet("/respostasPostagem/{idPostagem:int}", async (int idPostagem, AppDbContext db) => 
{
    var respostas = await db.Respostas
                            .Where(c => c.IdPostagem == idPostagem)
                            .ToListAsync();
    var qtsRespostas = respostas.Count();
    return respostas.Any()
        ? Results.Ok(respostas)
        : Results.NotFound($"Nenhuma resposta encontrada para o post {idPostagem}");
});

app.Run();
