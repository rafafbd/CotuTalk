using System.Text.Json.Serialization;

public class Membro
{
    public int IdMembro { get; set; }
    public int IdUsuario { get; set; }
    public required Usuario Usuario { get; set; }
    public int Idgrupo { get; set; }
    public required Grupo Grupo { get; set; }
    public DateTime DataDeEntrada { get; set; }
}