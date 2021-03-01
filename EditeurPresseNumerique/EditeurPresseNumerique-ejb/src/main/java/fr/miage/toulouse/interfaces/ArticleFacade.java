/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.toulouse.interfaces;

import fr.miage.toulouse.entities.Article;
import fr.miage.toulouse.gestiondto.Constants;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author trongvo
 */
@Stateless
public class ArticleFacade extends AbstractFacade<Article> implements ArticleFacadeLocal {

    @EJB
    ArticleFacadeLocal articleFacadeLocal;
    
    @PersistenceContext(unitName = "EditeurPresseNumeriquePesistenceUnit")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ArticleFacade() {
        super(Article.class);
    }

    @Override
    public Article findByCodeArticle(String codeArticle){
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaQuery<Article> cq = cb.createQuery(Article.class);
        Root<Article> root = cq.from(Article.class);
        cq.where(
            cb.and(
                cb.equal(cb.upper(root.get("codeArticle").as(String.class)), codeArticle.toUpperCase())
            )
        );
        return getEntityManager().createQuery(cq).getSingleResult();
    }
    
    @Override
    public Article createArticle(String nameArticle, String codeArticle, String nameAuthor, List<String> keywords, String content, String date) {
        try{
            return findByCodeArticle(codeArticle);
        }catch(NoResultException e){
            Article article = new Article(nameArticle, codeArticle, nameAuthor, keywords, content, date);
            this.create(article);
            System.out.println(Constants.CREATE_SUCCES);
            System.out.println("Created "+article.toString());
            return article;
        }
    }
    
}
