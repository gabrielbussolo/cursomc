package pro.gabrielferreira.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pro.gabrielferreira.cursomc.domain.Categoria;

/*
 * @repository é tipo DAO, nao fiz nada aqui ainda, só herdei Jpa, 
 * passo pra ele a classe de dominio e o tipo do Id dela
 * JpaRepository ja tem varios metodos prontos, find, findall, getbyid. ps:tops
 */
@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
	/*
	 * base pra fazer findAll categorias pegando apenas o que eu quero e jogando pro dto direto do banco, pelo JOAO
	 * 
	 * @Query("SELECT new br.com.softplan.ungp.eng.sib.dto.importacaoshp.ImportacaoShpTrechoGeoToValidateDTO(g.cdImportShpGeo, g.importShpTrecho.cdImportShpTrecho, g.importShpTrecho.sgRodovia, g.gmInicio, g.gmFim) "
	 * + " FROM ImportacaoShpTrechoGeo g " // + " JOIN g.importShpTrecho " // +
	 * " JOIN g.importShpTrecho.importShp " // +
	 * " where g.importShpTrecho.importShp.cdImportShp = :cdImportShp ")
	 * List<ImportacaoShpTrechoGeoToValidateDTO>
	 * findByCdImportShpTrecho(@Param("cdImportShp") Long cdImportShp);
	 */
}
