using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace TWTodos.Migrations
{
    /// <inheritdoc />
    public partial class CorrigeRelacionamentoCurtida : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Curtida_Postagem_IdUsuario",
                table: "Curtida");

            migrationBuilder.CreateIndex(
                name: "IX_Curtida_IdPostagem",
                table: "Curtida",
                column: "IdPostagem");

            migrationBuilder.AddForeignKey(
                name: "FK_Curtida_Postagem_IdPostagem",
                table: "Curtida",
                column: "IdPostagem",
                principalTable: "Postagem",
                principalColumn: "IdPostagem",
                onDelete: ReferentialAction.Cascade);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Curtida_Postagem_IdPostagem",
                table: "Curtida");

            migrationBuilder.DropIndex(
                name: "IX_Curtida_IdPostagem",
                table: "Curtida");

            migrationBuilder.AddForeignKey(
                name: "FK_Curtida_Postagem_IdUsuario",
                table: "Curtida",
                column: "IdUsuario",
                principalTable: "Postagem",
                principalColumn: "IdPostagem",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
