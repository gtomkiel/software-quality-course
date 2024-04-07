package com.jabberpoint.accessor;

import com.jabberpoint.accessor.demo.DemoPresentation;
import com.jabberpoint.accessor.xml.XMLAccessor;

public class AccessorFactory {
    public static Accessor createAccessor(AccessorType accessorType) {
        return switch (accessorType) {
            case AccessorType.XML -> new XMLAccessor();
            case AccessorType.DEMO -> new DemoPresentation();
        };
    }
}
