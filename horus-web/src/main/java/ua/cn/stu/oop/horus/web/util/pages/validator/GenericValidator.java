package ua.cn.stu.oop.horus.web.util.pages.validator;

import ua.cn.stu.oop.horus.core.language.AvailableLocale;

/**
 *
 * @author alex
 */
public abstract class GenericValidator {

    public abstract String validateAndGetErrorMessageOrNull(AvailableLocale locale, Object... obj);
}
