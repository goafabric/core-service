package org.goafabric.core.data.controller.dto.types;

public enum TelecomSystem {
    /**
     * The value is a telephone number used for voice calls. Use of full international numbers starting with + is recommended to enable automatic dialing support but not required.
     */
    PHONE("phone"),
    /**
     * The value is a fax machine. Use of full international numbers starting with + is recommended to enable automatic dialing support but not required.
     */
    FAX("fax"),
    /**
     * The value is an email address.
     */
    EMAIL("email"),
    /**
     * The value is a pager number. These may be local pager numbers that are only usable on a particular pager system.
     */
    PAGER("pager"),
    /**
     * A contact that is not a phone, fax, pager or email address and is expressed as a URL.  This is intended for various institutional or personal contacts including web sites, blogs, Skype, Twitter, Facebook, etc. Do not use for email addresses.
     */
    URL("url"),
    /**
     * A contact that can be used for sending an sms message (e.g. mobile phones, some landlines).
     */
    SMS("sms"),
    /**
     * A contact that is not a phone, fax, page or email address and is not expressible as a URL.  E.g. Internal mail address.  This SHOULD NOT be used for contacts that are expressible as a URL (e.g. Skype, Twitter, Facebook, etc.)  Extensions may be used to distinguish "other" contact types.
     */
    OTHER("other"),
    /**
     * added to help the parsers with the generic types
     */
    NULL("null");

    private String value;

    TelecomSystem(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
