package com.example.demo.notification;

import lombok.Getter;

@Getter
public enum ExceptionMessage {

      EXCEPTION_MEDIA_OFFER_TEST_CASE_NOT_FOUND("EXCEPTION_MEDIA_OFFER_TEST_CASE_NOT_FOUND"),
      EXCEPTION_MEDIA_OFFER_TEST_GROUP_NOT_FOUND("EXCEPTION_MEDIA_OFFER_TEST_GROUP_NOT_FOUND"),
      EXCEPTION_MEDIA_OFFER_TEST_GROUP_NOT_FOUND_FOR_GROUP("EXCEPTION_MEDIA_OFFER_TEST_GROUP_NOT_FOUND_FOR_GROUP"),
      EXCEPTION_COMPANY_NOT_FOUND_BY_ID("EXCEPTION_COMPANY_NOT_FOUND_BY_ID"),
      EXCEPTION_TEST_PROVIDER_NOT_FOUND_BY_ID("EXCEPTION_TEST_PROVIDER_NOT_FOUND_BY_ID"),
      EXCEPTION_EDUCHECK_COMPANY_NOT_FOUND("EXCEPTION_EDUCHECK_COMPANY_NOT_FOUND"),
      EXCEPTION_USER_NOT_FOUND_BY_ID("EXCEPTION_USER_NOT_FOUND_BY_ID"),
      EXCEPTION_USER_NOT_FOUND_BY_EMAIL("EXCEPTION_USER_NOT_FOUND_BY_EMAIL"),
      EXCEPTION_USER_COMPANY_NOT_FOUND("EXCEPTION_USER_COMPANY_NOT_FOUND"),
      EXCEPTION_ADDRESS_NOT_FOUND_BY_ID("EXCEPTION_ADDRESS_NOT_FOUND_BY_ID"),
      EXCEPTION_AUDIT_AREA_NOT_FOUND_BY_ID("EXCEPTION_AUDIT_AREA_NOT_FOUND_BY_ID"),
      EXCEPTION_AUDIT_SUBAREA_NOT_FOUND_BY_ID("EXCEPTION_AUDIT_SUBAREA_NOT_FOUND_BY_ID"),
      EXCEPTION_AUDIT_AREA_NOT_FOUND_BY_VALUE("EXCEPTION_AUDIT_AREA_NOT_FOUND_BY_VALUE"),
      EXCEPTION_AUDIT_CRITERIA_NOT_FOUND_BY_ID("EXCEPTION_AUDIT_CRITERIA_NOT_FOUND_BY_ID"),
      EXCEPTION_AUDIT_SUB_AREA_NOT_FOUND_BY_ID("EXCEPTION_AUDIT_SUB_AREA_NOT_FOUND_BY_ID"),
      EXCEPTION_CATALOG_NOT_FOUND_BY_ID("EXCEPTION_CATALOG_NOT_FOUND_BY_ID"),
      EXCEPTION_DRAFT_CATALOG_NOT_FOUND("EXCEPTION_DRAFT_CATALOG_NOT_FOUND"),
      EXCEPTION_CURRENT_AUDIT_CRITERIA_NOT_FOUND_FOR_SELF_DISCLOSURE("EXCEPTION_CURRENT_AUDIT_CRITERIA_NOT_FOUND_FOR_SELF_DISCLOSURE"),
      EXCEPTION_DICTIONARY_NOT_FOUND_BY_NAME("EXCEPTION_DICTIONARY_NOT_FOUND_BY_NAME"),
      EXCEPTION_REFERENCE_NOT_FOUND_BY_ID("EXCEPTION_REFERENCE_NOT_FOUND_BY_ID"),
      EXCEPTION_RELEVANCE_PROTECTION_NOT_FOUND_BY_ID("EXCEPTION_RELEVANCE_PROTECTION_NOT_FOUND_BY_ID"),
      EXCEPTION_SOURCE_NOT_FOUND_BY_ID("EXCEPTION_SOURCE_NOT_FOUND_BY_ID"),
      EXCEPTION_TEST_CASE_NOT_FOUND_BY_ID("EXCEPTION_TEST_CASE_NOT_FOUND_BY_ID"),
      EXCEPTION_DOCUMENT_NOT_FOUND_BY_ID("EXCEPTION_DOCUMENT_NOT_FOUND_BY_ID"),
      EXCEPTION_MEDIA_PROPERTY_NOT_FOUND_BY_ID("EXCEPTION_MEDIA_PROPERTY_NOT_FOUND_BY_ID"),
      EXCEPTION_MEDIA_PROPERTY_GROUP_NOT_FOUND_BY_ID("EXCEPTION_MEDIA_PROPERTY_GROUP_NOT_FOUND_BY_ID"),
      EXCEPTION_OS_NOT_FOUND_BY_ID("EXCEPTION_OS_NOT_FOUND_BY_ID"),
      EXCEPTION_OS_VERSION_NOT_FOUND_BY_ID("EXCEPTION_OS_VERSION_NOT_FOUND_BY_ID"),
      EXCEPTION_PLATFORM_NOT_FOUND_BY_ID("EXCEPTION_PLATFORM_NOT_FOUND_BY_ID"),
      EXCEPTION_PLATFORM_MAPPING_NOT_FOUND_BY_ID("EXCEPTION_PLATFORM_MAPPING_NOT_FOUND_BY_ID"),
      EXCEPTION_PERMISSION_NOT_FOUND("EXCEPTION_PERMISSION_NOT_FOUND"),
      EXCEPTION_TASK_NOT_FOUND_BY_ID("EXCEPTION_TASK_NOT_FOUND_BY_ID"),
      EXCEPTION_TEST_GROUP_NOT_FOUND_BY_ID("EXCEPTION_TEST_GROUP_NOT_FOUND_BY_ID"),
      EXCEPTION_MEDIA_OFFER_NOT_FOUND_BY_ID("EXCEPTION_MEDIA_OFFER_NOT_FOUND_BY_ID"),
      EXCEPTION_MEDIA_OFFER_NOT_FOUND_BY_MEDIA_REQUEST_ID("EXCEPTION_MEDIA_OFFER_NOT_FOUND_BY_MEDIA_REQUEST_ID"),
      EXCEPTION_NOTIFICATION_NOT_FOUND_BY_ID("EXCEPTION_NOTIFICATION_NOT_FOUND_BY_ID"),
      EXCEPTION_ONBOARDING_NOT_FOUND_BY_ID("EXCEPTION_ONBOARDING_NOT_FOUND_BY_ID"),
      EXCEPTION_SURVEY_RESULT_NOT_FOUND_BY_ID("EXCEPTION_SURVEY_RESULT_NOT_FOUND_BY_ID"),
      EXCEPTION_SURVEY_TEMPLATE_NOT_FOUND_BY_ID("EXCEPTION_SURVEY_TEMPLATE_NOT_FOUND_BY_ID"),
      EXCEPTION_SURVEY_TEMPLATE_NOT_FOUND_BY_FORM_KEY("EXCEPTION_SURVEY_TEMPLATE_NOT_FOUND_BY_FORM_KEY"),
      EXCEPTION_SURVEY_TEMPLATE_NOT_FOUND_BY_ID_OR_KEY("EXCEPTION_SURVEY_TEMPLATE_NOT_FOUND_BY_ID_OR_KEY"),
      EXCEPTION_SURVEY_TEMPLATE_NOT_FOUND_BY_ID_AND_TYPE("EXCEPTION_SURVEY_TEMPLATE_NOT_FOUND_BY_ID_AND_TYPE"),
      EXCEPTION_SURVEY_TEMPLATE_REVISION_NOT_FOUND_BY_ID("EXCEPTION_SURVEY_TEMPLATE_REVISION_NOT_FOUND_BY_ID"),
      EXCEPTION_INVALID_SURVEY_REVISION_ID("EXCEPTION_INVALID_SURVEY_REVISION_ID"),
      EXCEPTION_TEST_PROVIDER_ALREADY_ASSIGNED("EXCEPTION_TEST_PROVIDER_ALREADY_ASSIGNED"),
      EXCEPTION_TEST_PROVIDER_IS_ONLY_ONE("EXCEPTION_TEST_PROVIDER_IS_ONLY_ONE"),
      EXCEPTION_TEST_PROVIDER_ASSIGNED_TO_ACTIVE_TEST("EXCEPTION_TEST_PROVIDER_ASSIGNED_TO_ACTIVE_TEST"),
      EXCEPTION_ARCHIVE_FORBIDDEN_FOR_EDUCHECK_OR_VIEWER("EXCEPTION_ARCHIVE_FORBIDDEN_FOR_EDUCHECK_OR_VIEWER"),
      EXCEPTION_COMPANY_HAS_OPEN_TASKS("EXCEPTION_COMPANY_HAS_OPEN_TASKS"),
      EXCEPTION_TASKS_CANNOT_BE_REASSIGNED_TO_ARCHIVED_COMPANY("EXCEPTION_TASKS_CANNOT_BE_REASSIGNED_TO_ARCHIVED_COMPANY"),
      EXCEPTION_NO_CURRENT_USER_CONTEXT("EXCEPTION_NO_CURRENT_USER_CONTEXT"),
      EXCEPTION_CURRENT_USER_NOT_AVAILABLE_BY_ID("EXCEPTION_CURRENT_USER_NOT_AVAILABLE_BY_ID"),
      EXCEPTION_NO_COMPANY("EXCEPTION_NO_COMPANY"),
      EXCEPTION_USER_HAS_NO_ROLES("EXCEPTION_USER_HAS_NO_ROLES"),
      EXCEPTION_CANNOT_ARCHIVE_USER_UNKNOWN_ROLE("EXCEPTION_CANNOT_ARCHIVE_USER_UNKNOWN_ROLE"),
      EXCEPTION_LINK_EXPIRED("EXCEPTION_LINK_EXPIRED"),
      EXCEPTION_USER_ALREADY_EXISTS_BY_EMAIL("EXCEPTION_USER_ALREADY_EXISTS_BY_EMAIL"),
      EXCEPTION_USER_NOT_ENABLED_CANNOT_UPDATE_EMAIL("EXCEPTION_USER_NOT_ENABLED_CANNOT_UPDATE_EMAIL"),
      EXCEPTION_USER_NOT_IN_SYSTEM("EXCEPTION_USER_NOT_IN_SYSTEM"),
      EXCEPTION_USER_LOCKED_OR_ARCHIVED("EXCEPTION_USER_LOCKED_OR_ARCHIVED"),
      EXCEPTION_INVITE_NEW_ACCOUNT_MANAGER("EXCEPTION_INVITE_NEW_ACCOUNT_MANAGER"),
      EXCEPTION_CANNOT_UPDATE_USER_ROLE_UNKNOWN("EXCEPTION_CANNOT_UPDATE_USER_ROLE_UNKNOWN"),
      EXCEPTION_CANNOT_DELETE_REFERENCE_IN_USE("EXCEPTION_CANNOT_DELETE_REFERENCE_IN_USE"),
      EXCEPTION_CANNOT_DELETE_SOURCE_IN_USE("EXCEPTION_CANNOT_DELETE_SOURCE_IN_USE"),
      EXCEPTION_SOURCE_ALREADY_EXISTS("EXCEPTION_SOURCE_ALREADY_EXISTS"),
      EXCEPTION_EMAIL_SENDING_FAILED("EXCEPTION_EMAIL_SENDING_FAILED"),
      EXCEPTION_SUBSCRIPTION_FAILED("EXCEPTION_SUBSCRIPTION_FAILED"),
      EXCEPTION_NOTIFICATION_INVALID_CONTENT("EXCEPTION_NOTIFICATION_INVALID_CONTENT"),
      EXCEPTION_JSON_PARSING_FAILED("EXCEPTION_JSON_PARSING_FAILED"),
      EXCEPTION_DATA_PARSING_FAILED("EXCEPTION_DATA_PARSING_FAILED"),
      EXCEPTION_TEMPLATE_COMPILATION_FAILED("EXCEPTION_TEMPLATE_COMPILATION_FAILED"),
      EXCEPTION_IMPORT_FILE_READ_FAILED("EXCEPTION_IMPORT_FILE_READ_FAILED"),
      EXCEPTION_CSV_READING_FAILED("EXCEPTION_CSV_READING_FAILED"),
      EXCEPTION_PLATFORM_ALREADY_HAS_MAPPING_NO_OS("EXCEPTION_PLATFORM_ALREADY_HAS_MAPPING_NO_OS"),
      EXCEPTION_PLATFORM_HAS_NO_OS("EXCEPTION_PLATFORM_HAS_NO_OS"),
      EXCEPTION_DOCUMENT_ALREADY_ATTACHED("EXCEPTION_DOCUMENT_ALREADY_ATTACHED"),
      EXCEPTION_DOCUMENT_UPLOAD_COMMENT_FAILED("EXCEPTION_DOCUMENT_UPLOAD_COMMENT_FAILED"),
      EXCEPTION_TASK_COMMENT_UPLOAD_FAILED("EXCEPTION_TASK_COMMENT_UPLOAD_FAILED"),
      EXCEPTION_USER_NO_PERMISSION_TO_COMPLETE_TASK("EXCEPTION_USER_NO_PERMISSION_TO_COMPLETE_TASK"),
      EXCEPTION_USER_NO_PERMISSION_TASK_UNASSIGNED("EXCEPTION_USER_NO_PERMISSION_TASK_UNASSIGNED"),
      EXCEPTION_TASK_CANNOT_SUBMIT_BATCH("EXCEPTION_TASK_CANNOT_SUBMIT_BATCH"),
      EXCEPTION_UNKNOWN_CONTEXT_KEY_FOR_TASK_TYPE("EXCEPTION_UNKNOWN_CONTEXT_KEY_FOR_TASK_TYPE"),
      EXCEPTION_CANNOT_ARCHIVE_MEDIA_OFFER("EXCEPTION_CANNOT_ARCHIVE_MEDIA_OFFER"),
      EXCEPTION_INVALID_STATUS_FOR_MEDIA_REQUEST("EXCEPTION_INVALID_STATUS_FOR_MEDIA_REQUEST"),
      EXCEPTION_MEDIA_OFFER_NOT_IN_RELEVANT_INSPECTION("EXCEPTION_MEDIA_OFFER_NOT_IN_RELEVANT_INSPECTION"),
      EXCEPTION_MEDIA_OFFER_NOT_IN_PERMISSION("EXCEPTION_MEDIA_OFFER_NOT_IN_PERMISSION"),
      EXCEPTION_MEDIA_OFFER_NOT_IN_PREPARATORY_AUDIT("EXCEPTION_MEDIA_OFFER_NOT_IN_PREPARATORY_AUDIT"),
      EXCEPTION_MEDIA_OFFER_NOT_IN_PLANNING_EXAM("EXCEPTION_MEDIA_OFFER_NOT_IN_PLANNING_EXAM"),
      EXCEPTION_NO_TEST_CASES_FOR_MEDIA_OFFER("EXCEPTION_NO_TEST_CASES_FOR_MEDIA_OFFER"),
      EXCEPTION_NO_TEST_GROUPS_FOR_MEDIA_OFFER("EXCEPTION_NO_TEST_GROUPS_FOR_MEDIA_OFFER"),
      EXCEPTION_NO_TEST_PROVIDERS_FOR_TEST_GROUP("EXCEPTION_NO_TEST_PROVIDERS_FOR_TEST_GROUP"),
      EXCEPTION_REVIEW_REQUIRES_AUDIT_CRITERIA("EXCEPTION_REVIEW_REQUIRES_AUDIT_CRITERIA"),
      EXCEPTION_REVIEW_REQUIRES_AUDIT_CRITERIA_WITH_JOB_ID("EXCEPTION_REVIEW_REQUIRES_AUDIT_CRITERIA_WITH_JOB_ID"),
      EXCEPTION_SUBMIT_ROUGH_REQUIRES_TEST_CASES("EXCEPTION_SUBMIT_ROUGH_REQUIRES_TEST_CASES"),
      EXCEPTION_SUBMIT_ROUGH_REQUIRES_AUDIT_CRITERIA("EXCEPTION_SUBMIT_ROUGH_REQUIRES_AUDIT_CRITERIA"),
      EXCEPTION_TEST_CASES_MISSING_PROVIDERS("EXCEPTION_TEST_CASES_MISSING_PROVIDERS"),
      EXCEPTION_MEDIA_PROPERTIES_NOT_FOUND("EXCEPTION_MEDIA_PROPERTIES_NOT_FOUND"),
      EXCEPTION_FILL_RFI_REQUIRES_MEDIA_PROPERTIES("EXCEPTION_FILL_RFI_REQUIRES_MEDIA_PROPERTIES"),
      EXCEPTION_TEST_PROVIDERS_REQUIRED("EXCEPTION_TEST_PROVIDERS_REQUIRED"),
      EXCEPTION_OS_REQUIRED_FOR_ROUGH_VERSION("EXCEPTION_OS_REQUIRED_FOR_ROUGH_VERSION"),
      EXCEPTION_OS_REQUIRED_FOR_SELF_DISCLOSURE("EXCEPTION_OS_REQUIRED_FOR_SELF_DISCLOSURE"),
      EXCEPTION_OPERATING_SYSTEMS_REQUIRED("EXCEPTION_OPERATING_SYSTEMS_REQUIRED"),
      EXCEPTION_OS_VERSIONS_REQUIRED("EXCEPTION_OS_VERSIONS_REQUIRED"),
      EXCEPTION_NO_AUDIT_CRITERIA_FOR_SELF_DISCLOSURE("EXCEPTION_NO_AUDIT_CRITERIA_FOR_SELF_DISCLOSURE"),
      EXCEPTION_NO_TEST_CASES_FOR_SELF_DISCLOSURE("EXCEPTION_NO_TEST_CASES_FOR_SELF_DISCLOSURE"),
      EXCEPTION_SELF_DISCLOSURE_REQUIRES_AUDIT_CRITERIA("EXCEPTION_SELF_DISCLOSURE_REQUIRES_AUDIT_CRITERIA"),
      EXCEPTION_ESTIMATION_REQUIRED_FOR_TEST_GROUP("EXCEPTION_ESTIMATION_REQUIRED_FOR_TEST_GROUP"),
      EXCEPTION_PERIOD_REQUIRED_FOR_TEST_PROVIDER("EXCEPTION_PERIOD_REQUIRED_FOR_TEST_PROVIDER"),
      EXCEPTION_FULL_PERIOD_REQUIRED("EXCEPTION_FULL_PERIOD_REQUIRED"),
      EXCEPTION_VALIDATION_FAILED("EXCEPTION_VALIDATION_FAILED"),
      EXCEPTION_ACCESS_DENIED("EXCEPTION_ACCESS_DENIED"),
      EXCEPTION_UNAUTHORIZED("EXCEPTION_UNAUTHORIZED"),
      EXCEPTION_UPLOAD_TOO_LARGE("EXCEPTION_UPLOAD_TOO_LARGE"),
      EXCEPTION_UPLOAD_MAX_SIZE("EXCEPTION_UPLOAD_MAX_SIZE"),
      EXCEPTION_RESOURCE_NOT_FOUND("EXCEPTION_RESOURCE_NOT_FOUND");

      private final String exceptionMessage;

      ExceptionMessage(String message) {
          this.exceptionMessage = message;
      }
}
