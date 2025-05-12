using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseSqlite("Data Source=twtdb.db"));

var app = builder.Build();

// Rota raiz
app.MapGet("/", () => "Hello World!");


// USUARIO
// Adicionar um usuario
app.MapPost("/usuarios", async (Usuario usuario, AppDbContext db) =>
{
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

// Verifiar login
app.MapPost("/login", async (LoginRequest login, AppDbContext db) => {
    var usuarios = await db.Usuarios.ToArrayAsync();
    bool validacao = false;
    foreach (var user in usuarios) 
    {
        if (user.Nome == login.Nome && user.Senha == login.Senha)
        {
            validacao = true;
            break;
        }
    }
    return Results.Ok(new {validacao});
});

// POSTAGEM
// Adicionar um novo post
app.MapPost("/postagens", async (Postagem postagem, AppDbContext db) => 
{
    db.Postagens.Add(postagem);
    await db.SaveChangesAsync();
    return Results.Created($"/postagens/{postagem.IdPostagem}", postagem);
});

// Listar posts
app.MapGet("/postagens", async (AppDbContext db) => 
{
    var postagens = await db.Postagens.ToListAsync();
    return Results.Ok(postagens);
});

// Posts de usuario
app.MapGet("/postagensUsuario/{IdUsuario:int}", async (int idUsuario, AppDbContext db) => 
{
    var postagens = await db.Postagens
                            .Where(p => p.IdUsuario == idUsuario)
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
app.MapPost("/grupos", async (Grupo grupo, AppDbContext db) => 
{
    grupo.DataCriacao = DateTime.UtcNow; 
    db.Grupos.Add(grupo);
    await db.SaveChangesAsync();
    return Results.Created($"/grupos/{grupo.IdGrupo}", grupo);
});

// Listar grupos
app.MapGet("/grupos", async (AppDbContext db) => 
{
    var grupos = await db.Curtidas.ToListAsync();
    return Results.Ok(grupos);
});

// CURTIDA   
// Adicionar curtida
app.MapPost("/curtidas", async (Curtida curtida, AppDbContext db) => 
{
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

// Curtidas de um usuario
app.MapGet("/curtidasUsuario/{IdUsuario:int}", async (int idUsuario, AppDbContext db) => 
{
    var curtidas = await db.Curtidas
                            .Where(c => c.IdUsuario == idUsuario)
                            .ToListAsync();
    var qtsCurtidas = curtidas.Count();
    return curtidas.Any()
        ? Results.Ok(new { qtsCurtidas, curtidas })
        : Results.NotFound($"Nenhuma curtida encontrada para o usuario {idUsuario}");
});

// Curtidas de um post
app.MapGet("/curtidasPostagem/{idPostagem:int}", async (int idPostagem, AppDbContext db) => 
{
    var curtidas = await db.Curtidas
                            .Where(c => c.IdPostagem == idPostagem)
                            .ToListAsync();
    var qtsCurtidas = curtidas.Count();
    return curtidas.Any()
        ? Results.Ok(new { qtsCurtidas, curtidas })
        : Results.NotFound($"Nenhuma curtida encontrada para o post {idPostagem}");
});

// RESPOSTA
// Adicionar resposta
app.MapPost("/respostas", async (Resposta resposta, AppDbContext db) => 
{
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

// Respostas de um usuario
app.MapGet("/respostasUsuario/{IdUsuario:int}", async (int idUsuario, AppDbContext db) => 
{
    var respostas = await db.Respostas
                            .Where(c => c.IdUsuario == idUsuario)
                            .ToListAsync();
    var qtsRespostas = respostas.Count();
    return respostas.Any()
        ? Results.Ok(new { qtsRespostas, respostas })
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
        ? Results.Ok(new { qtsRespostas, respostas })
        : Results.NotFound($"Nenhuma resposta encontrada para o post {idPostagem}");
});

app.Run();
