package com.jabberpoint.accessor;

public class AccessorFactory {
    public static Accessor createAccessor(XMLAccessorType accessorType) {
        return switch (accessorType) {
            case XMLAccessorType.XML -> new XMLAccessor();
            case XMLAccessorType.DEMO -> new DemoPresentation();
        };
    }
}
