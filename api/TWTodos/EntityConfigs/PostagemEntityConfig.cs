using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

public class PostagemEntityConfig : IEntityTypeConfiguration<Postagem>
{
    public void Configure(EntityTypeBuilder<Postagem> builder)
    {
        builder.ToTable("Postagem");


        builder.HasKey(p => p.IdPostagem);

        builder.Property(u => u.Conteudo)
               .IsRequired()
               .HasMaxLength(50);

        builder.HasOne(p => p.Usuario)
            .WithMany(u => u.Postagens)             // diz que o usuario pode ter n postagens, rel 1 - N
            .HasForeignKey(p => p.IdUsuario)        // define foreign key
            .OnDelete(DeleteBehavior.Cascade);     // delete cascade

        // -> não esta funcionando:
        // builder.HasOne(p => p.Grupo)
        //     .WithMany(u => u.Postagens)
        //     .HasForeignKey(p => p.IdGrupo)
        //     .OnDelete(DeleteBehavior.Cascade);
    }
}