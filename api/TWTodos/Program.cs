using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<AppDbContext>(options =>
    options.UseSqlite("Data Source=twtdb.db"));

var app = builder.Build();

//rota raiz
app.MapGet("/", () => "Hello World!");


//USUARIO
//adicionar um usuario
app.MapPost("/usuarios", async (Usuario usuario, AppDbContext db) =>
{
    db.Usuarios.Add(usuario);
    await db.SaveChangesAsync();
    return Results.Created($"/usuarios/{usuario.IdUsuario}", usuario);
});

//listar usuarios
app.MapGet("/usuarios", async (AppDbContext db) =>
{
    var usuarios = await db.Usuarios.ToListAsync();
    return Results.Ok(usuarios);
});


//POSTAGEM
//adicionar um novo post
app.MapPost("/postagens", async (Postagem postagem, AppDbContext db) => 
{
    db.Postagens.Add(postagem);
    await db.SaveChangesAsync();
    return Results.Created($"/postagens/{postagem.IdPostagem}", postagem);
});

//listar posts
app.MapGet("/postagens", async (Postagem postagem, AppDbContext db) => 
{
    var postagens = await db.Postagens.ToListAsync();
    return Results.Ok(postagens);
});

//posts de usuario
app.MapGet("/postagens/{IdUsuario:int}", async (int idUsuario, AppDbContext db) => 
{
    var postagens = await db.Postagens
                            .Where(p => p.IdUsuario == idUsuario)
                            .ToListAsync();
    return postagens.Any()
        ? Results.Ok(postagens)
        : Results.NotFound($"Nenhuma postagem encontrada para o usuário {idUsuario}");
});


//GRUPO
//adicionar um grupo
app.MapPost("/grupos", async (Grupo grupo, AppDbContext db) => 
{
    
});
//MEMBRO


//CURTIDA   
//adicionar curtida
app.MapPost("/curtidas", async (Curtida curtida, AppDbContext db) => 
{
    db.Curtidas.Add(curtida);
    await db.SaveChangesAsync();
    return Results.Created($"/curtidas/{curtida.IdCurtida}", curtida);
});

//listar curtidas 
app.MapGet("/curtidas", async (Curtida curtida, AppDbContext db) =>
{
    var curtidas = db.Curtidas.ToListAsync();
    return Results.Ok(curtidas);
});

//curtidas de um usuario
app.MapGet("/curtidasUsuario/{IdUsuario:int}", async (int idUsuario, AppDbContext db) => 
{
    var curtidas = await db.Curtidas
                            .Where(c => c.IdUsuario == idUsuario)
                            .ToListAsync();
    var qtsCurtidas = curtidas.Count();
    return curtidas.Any()
        ? Results.Ok(new {qtsCurtidas, curtidas})
        : Results.NotFound($"Nenhuma curtida encontrada para o usuario {idUsuario}");
});

//curtidas de um post
app.MapGet("/curtidasPostagem/{idPostagem:int}", async (int idPostagem, AppDbContext db) => 
{
    var curtidas = await db.Curtidas
                            .Where(c => c.IdPostagem == idPostagem)
                            .ToListAsync();
    var qtsCurtidas = curtidas.Count();
    return curtidas.Any()
        ? Results.Ok(new {qtsCurtidas, curtidas})
        : Results.NotFound($"Nenhuma curtida encontrada para o usuario {idPostagem}");
});


//RESPOSTA
//adicionar resposta
app.MapPost("/respostas", async (Resposta resposta, AppDbContext db) => 
{
    db.Respostas.Add(resposta);
    await db.SaveChangesAsync();
    return Results.Created($"/respostas/{resposta.IdResposta}", resposta);
});

//listar respostas 
app.MapGet("/respostas", async (Resposta resposta, AppDbContext db) =>
{
    var respostas = db.Respostas.ToListAsync();
    return Results.Ok(respostas);
});

//respostas de um usuario
app.MapGet("/respostasUsuario/{IdUsuario:int}", async (int idUsuario, AppDbContext db) => 
{
    var respostas = await db.Respostas
                            .Where(c => c.IdUsuario == idUsuario)
                            .ToListAsync();
    var qtsrespostas = respostas.Count();
    return respostas.Any()
        ? Results.Ok(new {qtsrespostas, respostas})
        : Results.NotFound($"Nenhuma resposta encontrada para o usuario {idUsuario}");
});

//respostas de um post
app.MapGet("/respostasresposta/{idresposta:int}", async (int idresposta, AppDbContext db) => 
{
    var respostas = await db.Respostas
                            .Where(c => c.IdResposta == idresposta)
                            .ToListAsync();
    var qtsrespostas = respostas.Count();
    return respostas.Any()
        ? Results.Ok(new {qtsrespostas, respostas})
        : Results.NotFound($"Nenhuma resposta encontrada para o usuario {idresposta}");
});


app.Run();