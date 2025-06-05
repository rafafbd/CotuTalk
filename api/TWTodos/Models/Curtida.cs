using System.Text.Json.Serialization;

public class Curtida
{
    public int IdCurtida { get; set; }
    public int IdUsuario { get; set; }
    public required Usuario Usuario { get; set; }
    public int IdPostagem { get; set; }
    public required Postagem Postagem { get; set; }
}
