using System;
using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace TWTodos.Migrations
{
    /// <inheritdoc />
    public partial class NomeDaNovaMigration : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.AddColumn<int>(
                name: "GrupoIdGrupo",
                table: "Postagem",
                type: "INTEGER",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.AddColumn<int>(
                name: "IdGrupo",
                table: "Postagem",
                type: "INTEGER",
                nullable: false,
                defaultValue: 0);

            migrationBuilder.CreateTable(
                name: "CodigosVerificacao",
                columns: table => new
                {
                    Id = table.Column<int>(type: "INTEGER", nullable: false)
                        .Annotation("Sqlite:Autoincrement", true),
                    email = table.Column<string>(type: "TEXT", nullable: false),
                    codigo = table.Column<string>(type: "TEXT", nullable: false),
                    DataDeGeracao = table.Column<DateTime>(type: "TEXT", nullable: false),
                    DataDeExpiracao = table.Column<DateTime>(type: "TEXT", nullable: false)
                },
                constraints: table =>
                {
                    table.PrimaryKey("PK_CodigosVerificacao", x => x.Id);
                });

            migrationBuilder.CreateIndex(
                name: "IX_Postagem_GrupoIdGrupo",
                table: "Postagem",
                column: "GrupoIdGrupo");

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

            migrationBuilder.DropTable(
                name: "CodigosVerificacao");

            migrationBuilder.DropIndex(
                name: "IX_Postagem_GrupoIdGrupo",
                table: "Postagem");

            migrationBuilder.DropColumn(
                name: "GrupoIdGrupo",
                table: "Postagem");

            migrationBuilder.DropColumn(
                name: "IdGrupo",
                table: "Postagem");
        }
    }
}
