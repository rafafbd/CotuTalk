using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

public class GrupoEntityConfig : IEntityTypeConfiguration<Grupo>
{
    public void Configure(EntityTypeBuilder<Grupo> builder)
    {
        builder.ToTable("Grupo");

        builder.HasKey(g => g.IdGrupo);
               

        builder.Property(g => g.Descricao)
               .IsRequired()
               .HasMaxLength(200);

        builder.Property(g => g.Nome)
               .IsRequired()
               .HasMaxLength(50);

        builder.Property(g => g.DataCriacao)
               .IsRequired();
            
    }
}