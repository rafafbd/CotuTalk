
public class PostagemUI
{
    public int IdPostagem { get; set; }
    public required string Conteudo { get; set; }
    public required Usuario Usuario { get; set; }
    public required Grupo Grupo { get; set; }

    public int qtsCurtidas { get; set; }
    public int qtsRespostas { get; set; }
}