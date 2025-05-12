public class CodigoVerificacao
{
    public int Id { get; set; }
    public string Email { get; set; }
    public string Codigo { get; set; }
    public DateTime DataDeGeracao { get; set; }
    public DateTime DataDeExpiracao { get; set; }
}