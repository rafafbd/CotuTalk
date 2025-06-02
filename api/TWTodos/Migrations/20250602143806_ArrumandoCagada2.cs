using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace TWTodos.Migrations
{
    /// <inheritdoc />
    public partial class ArrumandoCagada2 : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Postagem_Grupo_GrupoIdGrupo",
                table: "Postagem");

            migrationBuilder.AlterColumn<int>(
                name: "GrupoIdGrupo",
                table: "Postagem",
                type: "INTEGER",
                nullable: false,
                defaultValue: 0,
                oldClrType: typeof(int),
                oldType: "INTEGER",
                oldNullable: true);

            migrationBuilder.AddForeignKey(
                name: "FK_Postagem_Grupo_GrupoIdGrupo",
                table: "Postagem",
                column: "GrupoIdGrupo",
                principalTable: "Grupo",
                principalColumn: "IdGrupo",
                onDelete: ReferentialAction.Cascade);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_Postagem_Grupo_GrupoIdGrupo",
                table: "Postagem");

            migrationBuilder.AlterColumn<int>(
                name: "GrupoIdGrupo",
                table: "Postagem",
                type: "INTEGER",
                nullable: true,
                oldClrType: typeof(int),
                oldType: "INTEGER");

            migrationBuilder.AddForeignKey(
                name: "FK_Postagem_Grupo_GrupoIdGrupo",
                table: "Postagem",
                column: "GrupoIdGrupo",
                principalTable: "Grupo",
                principalColumn: "IdGrupo");
        }
    }
}
