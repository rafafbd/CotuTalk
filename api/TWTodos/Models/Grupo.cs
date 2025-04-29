public class Grupo
{
    public int IdGrupo { get; set; }
    public string Nome { get; set; } = string.Empty;
    public DateTime DataCriacao { get; set; }
    public string Descricao { get; set; } = string.Empty;
    public ICollection<Membro> Membros { get; set; } = new List<Membro>();
}