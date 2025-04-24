public class Membro
{
    public int IdMembro { get; set; }
    public int IdUsuario { get; set; }
    public Usuario Usuario { get; set; }
    public int Idgrupo { get; set; }
    public Grupo Grupo { get; set; }
    public DateTime DataDeEntrada { get; set; }
}