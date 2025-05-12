using Microsoft.EntityFrameworkCore;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

    protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
    {
        if (!optionsBuilder.IsConfigured)
        {
            optionsBuilder.UseSqlite("Data Source=twapi.sqlite3");
        }
    }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        modelBuilder.ApplyConfiguration(new CurtidaEntityConfig());
        modelBuilder.ApplyConfiguration(new GrupoEntityConfig());
        modelBuilder.ApplyConfiguration(new MembroEntityConfig());
        modelBuilder.ApplyConfiguration(new PostagemEntityConfig());
        modelBuilder.ApplyConfiguration(new RespostaEntityConfig());
        modelBuilder.ApplyConfiguration(new UsuarioEntityConfig());
    }

    public DbSet<Usuario> Usuarios { get; set; }
    public DbSet<Grupo> Grupos { get; set; }
    public DbSet<Postagem> Postagens { get; set; }
    public DbSet<Membro> Membros { get; set; }
    public DbSet<Resposta> Respostas { get; set; }
    public DbSet<Curtida> Curtidas { get; set; }
    public DbSet<CodigoVerificacao> CodigosVerificacao { get; set; }
}
