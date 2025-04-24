using Microsoft.EntityFrameworkCore;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        optionsBuilder.UseSqlite("twapi.sqlite3");
    }

    public DbSet<Usuario> Usuarios { get; set; }
    public DbSet<Grupo> grupos { get; set; }
    public DbSet<Postagem> Postagens { get; set; }
    public DbSet<Membro> membros { get; set; }
    public DbSet<Resposta> respostas { get; set; }
    public DbSet<Curtida> curtidas { get; set; }
}
