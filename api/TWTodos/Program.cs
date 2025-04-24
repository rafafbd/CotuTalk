using Microsoft.EntityFrameworkCore;

var builder = WebApplication.CreateBuilder(args);
var app = builder.Build();

builder.Services.AddDbContext<AppDbContext>(options => options.UseSqlite("Data Source=twtdb.db"));

app.MapGet("/", () => "Hello World!");

app.Run();
