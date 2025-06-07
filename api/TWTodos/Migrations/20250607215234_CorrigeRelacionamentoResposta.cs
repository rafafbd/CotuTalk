using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace TWTodos.Migrations
{
    /// <inheritdoc />
    public partial class CorrigeRelacionamentoResposta : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Resposta_Postagem_IdUsuario",
                table: "Resposta");

            migrationBuilder.CreateIndex(
                name: "IX_Resposta_IdPostagem",
                table: "Resposta",
                column: "IdPostagem");

            migrationBuilder.AddForeignKey(
                name: "FK_Resposta_Postagem_IdPostagem",
                table: "Resposta",
                column: "IdPostagem",
                principalTable: "Postagem",
                principalColumn: "IdPostagem",
                onDelete: ReferentialAction.Cascade);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Resposta_Postagem_IdPostagem",
                table: "Resposta");

            migrationBuilder.DropIndex(
                name: "IX_Resposta_IdPostagem",
                table: "Resposta");

            migrationBuilder.AddForeignKey(
                name: "FK_Resposta_Postagem_IdUsuario",
                table: "Resposta",
                column: "IdUsuario",
                principalTable: "Postagem",
                principalColumn: "IdPostagem",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
