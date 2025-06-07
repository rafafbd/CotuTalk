using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

public class CurtidaEntityConfig : IEntityTypeConfiguration<Curtida>
{
    public void Configure(EntityTypeBuilder<Curtida> builder)
    {
        builder.ToTable("Curtida");


        builder.HasKey(c => c.IdCurtida);

        builder.HasOne(c => c.Usuario)
            .WithMany(u => u.Curtidas)
            .HasForeignKey(c => c.IdUsuario)
            .OnDelete(DeleteBehavior.Cascade);

        builder.HasOne(c => c.Postagem)
            .WithMany(p => p.Curtidas)
            .HasForeignKey(c => c.IdPostagem)
            .OnDelete(DeleteBehavior.Cascade);
               
    }
}