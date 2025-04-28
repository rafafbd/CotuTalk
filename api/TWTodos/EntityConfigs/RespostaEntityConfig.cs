using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

public class RespostaEntityConfig : IEntityTypeConfiguration<Resposta>
{
    public void Configure(EntityTypeBuilder<Resposta> builder)
    {
        builder.ToTable("Resposta");


        builder.HasKey(r => r.IdResposta);

        builder.Property(r => r.Conteudo)
            .HasMaxLength(50);

        builder.HasOne(r => r.Usuario)
            .WithMany(u => u.Respostas)
            .HasForeignKey(r => r.IdUsuario)
            .OnDelete(DeleteBehavior.Cascade);

        builder.HasOne(r => r.Postagem)
            .WithMany(p => p.Respostas)
            .HasForeignKey(r => r.IdUsuario)
            .OnDelete(DeleteBehavior.Cascade);
               
    }
}