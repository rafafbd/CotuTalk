using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Metadata.Builders;

public class MembroEntityConfig : IEntityTypeConfiguration<Membro>
{
    public void Configure(EntityTypeBuilder<Membro> builder)
    {
        builder.ToTable("Membro");


        builder.HasKey(m => m.IdMembro);

        builder.HasOne(m => m.Usuario)
            .WithMany(u => u.Membro)             
            .HasForeignKey(m => m.IdUsuario)        
            .OnDelete(DeleteBehavior.Cascade);     


        builder.HasOne(m => m.Grupo)
            .WithMany(g => g.Membro)
            .HasForeignKey(m => m.IdUsuario)  
            .OnDelete(DeleteBehavior.Cascade);
    }
}