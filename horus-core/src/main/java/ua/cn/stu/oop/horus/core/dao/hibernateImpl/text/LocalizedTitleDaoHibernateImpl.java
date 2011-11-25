package ua.cn.stu.oop.horus.core.dao.hibernateImpl.text;

import java.util.Collection;
import org.springframework.stereotype.Repository;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.GenericDaoHibernateImpl;
import ua.cn.stu.oop.horus.core.dao.hibernateImpl.genericqueries.*;
import ua.cn.stu.oop.horus.core.dao.text.LocalizedTitleDao;
import ua.cn.stu.oop.horus.core.domain.text.*;
import ua.cn.stu.oop.horus.core.language.*;

/**
 *
 * @author alex
 */
@Repository("LocalizedTitleDaoHibernateImpl")
public class LocalizedTitleDaoHibernateImpl
        extends GenericDaoHibernateImpl<LocalizedTitle>
        implements LocalizedTitleDao {

    public LocalizedTitleDaoHibernateImpl() {
        super(LocalizedTitle.class);
    }

    @Override
    public Collection<TitleLink> getAllTitleLinks() {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getAllTitleLinks(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public LocalizedTitle getEntityOrNullByTitleLink(TitleLink titleLink) {
        Class cls = getEntityClass();
        String query = TitleLinkCarrierQueries.
                getCarriersByTitleLink(cls);
        return (LocalizedTitle) singleResultOrNullByQuery(query, titleLink);
    }

    @Override
    public Collection<String> getAllTitles() {
        Class cls = getEntityClass();
        String query = TitleCarrierQueries.
                getAllTitles(cls);
        return getHibernateTemplate().find(query);
    }

    @Override
    public LocalizedTitle getEntityOrNullByTitle(String title) {
        Class cls = getEntityClass();
        String query = TitleCarrierQueries.
                getTitleCarriersByTitle(cls);
        return (LocalizedTitle) singleResultOrNullByQuery(query, title);
    }

    @Override
    public Collection<LocalizedTitle> getTitlesWithDefaultGrammarByTitleLinkId(Long id) {
        return multipleResultByNamedQuery(
                    "findWithGrammarByTitleLinkIdNativeSQL",
                    GrammaticalCase.getDefault().name(),
                    GrammaticalGender.getDefault().name(),
                    GrammaticalNumber.getDefault().name(),
                    PartOfSpeech.getDefault().name(),
                    id);
    }

    @Override
    public LocalizedTitle getTitleWithDefaultGrammarByTitleLinkIdAndLocale(Long id, AvailableLocale locale) {
        return (LocalizedTitle)
                singleResultOrNullByNamedQuery(
                    "findWithGrammarByTitleLinkIdAndLocaleNativeSQL",
                    GrammaticalCase.getDefault().name(),
                    GrammaticalGender.getDefault().name(),
                    GrammaticalNumber.getDefault().name(),
                    PartOfSpeech.getDefault().name(),
                    id,
                    locale.name());
    }
}
