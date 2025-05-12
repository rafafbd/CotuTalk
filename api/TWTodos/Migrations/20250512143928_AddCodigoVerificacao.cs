using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace TWTodos.Migrations
{
    /// <inheritdoc />
    public partial class AddCodigoVerificacao : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "email",
                table: "CodigosVerificacao",
                newName: "Email");

            migrationBuilder.RenameColumn(
                name: "codigo",
                table: "CodigosVerificacao",
                newName: "Codigo");
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.RenameColumn(
                name: "Email",
                table: "CodigosVerificacao",
                newName: "email");

            migrationBuilder.RenameColumn(
                name: "Codigo",
                table: "CodigosVerificacao",
                newName: "codigo");
        }
    }
}
