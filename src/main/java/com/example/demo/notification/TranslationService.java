package com.example.demo.notification;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class TranslationService {

    private final MessageSource messageSource;

    public String translate(String messageKey) {
        return translate(messageKey, List.of());
    }

    public String translate(ExceptionMessage key, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        List<String> argList = args != null ? Arrays.stream(args).map(String::valueOf).toList() : List.of();
        return getStaticValueInLocale(key.name(), argList, locale);
    }

    public String translate(String key, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        List<String> argList = args != null ? Arrays.stream(args).map(String::valueOf).toList() : List.of();
        return getStaticValueInLocale(key, argList, locale);
    }

    public String getStaticValueInLocale(String messageKey, List<String> args, Locale locale) {
        try {
            return messageSource.getMessage(messageKey.toUpperCase(), args.toArray(), locale);
        } catch (NoSuchMessageException e) {
            log.error("Translation not found for key '{}' and locale '{}'", messageKey, locale);
            return messageKey.toLowerCase();
        }
    }
}


//    public String getExceptionMessage(ExceptionMessage messageKey, List<String> args, Locale locale) {
//        try {
//            return messageSource.getMessage(messageKey.name().toUpperCase(), args.toArray(), locale);
//        } catch (NoSuchMessageException e) {
//            log.error("Translation not found for key '{}' and locale '{}'", messageKey, locale);
//            return messageKey.name().toLowerCase();
//        }
//    }



//    public String translate(ExceptionMessage key, List<String> args) {
//        Locale locale = LocaleContextHolder.getLocale();
//        return getStaticValueInLocale(key, args, locale);
//    }


//    public String translateOrDefault(String primaryKey, String fallbackKey, Object... args) {
//        Locale locale = LocaleContextHolder.getLocale();
//        List<String> argList = args != null ? Arrays.stream(args).map(String::valueOf).toList() : List.of();
//
//        String upperPrimary = primaryKey.toUpperCase();
//        String upperFallback = fallbackKey.toUpperCase();
//
//      if (messageSourceResolvableExists(upperPrimary, argList, locale)) {
//          return messageSource.getMessage(upperPrimary, argList.toArray(), locale);
//      }
//
//      if (messageSourceResolvableExists(upperFallback, argList, locale)) {
//          return messageSource.getMessage(upperFallback, argList.toArray(), locale);
//      }
//
//        log.error("Neither translation key '{}' nor fallback '{}' found for locale '{}'", upperPrimary, upperFallback, locale);
//        return fallbackKey.toLowerCase();

//    private boolean messageSourceResolvableExists(String key, List<String> args, Locale locale) {
//        try {
//            messageSource.getMessage(key, args.toArray(), locale);
//            return true;
//        } catch (NoSuchMessageException e) {
//            return false;
//        }
//    }