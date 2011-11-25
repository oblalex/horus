package ua.cn.stu.oop.horus.core.domain.text;

import ua.cn.stu.oop.horus.core.domain.GenericDomain;
import ua.cn.stu.oop.horus.core.language.*;

/**
 *
 * @author alex
 */
public class LocalizedTitle
        extends GenericDomain
        implements StringTitleCarrier, TitleLinkCarrier {

    private TitleLink titleLink;
    private String title;
    private AvailableLocale locale;
    private PartOfSpeech partOfSpeech = PartOfSpeech.getDefault();
    private GrammaticalGender grammaticalGender = GrammaticalGender.getDefault();
    private GrammaticalNumber grammaticalNumber = GrammaticalNumber.getDefault();
    private GrammaticalCase grammaticalCase = GrammaticalCase.getDefault();

    public GrammaticalCase getGrammaticalCase() {
        return grammaticalCase;
    }

    public void setGrammaticalCase(GrammaticalCase grammaticalCase) {
        this.grammaticalCase = grammaticalCase;
    }

    public GrammaticalNumber getGrammaticalNumber() {
        return grammaticalNumber;
    }

    public void setGrammaticalNumber(GrammaticalNumber grammaticalNumber) {
        this.grammaticalNumber = grammaticalNumber;
    }

    public GrammaticalGender getGrammaticalGender() {
        return grammaticalGender;
    }

    public void setGrammaticalGender(GrammaticalGender grammaticalGender) {
        this.grammaticalGender = grammaticalGender;
    }

    public PartOfSpeech getPartOfSpeech() {
        return partOfSpeech;
    }

    public void setPartOfSpeech(PartOfSpeech partOfSpeech) {
        this.partOfSpeech = partOfSpeech;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    public AvailableLocale getLocale() {
        return locale;
    }

    public void setLocale(AvailableLocale locale) {
        this.locale = locale;
    }

    @Override
    public TitleLink getTitleLink() {
        return titleLink;
    }

    @Override
    public void setTitleLink(TitleLink titleLink) {
        this.titleLink = titleLink;
    }
}
