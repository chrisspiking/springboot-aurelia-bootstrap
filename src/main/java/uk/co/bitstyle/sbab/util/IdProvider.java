package uk.co.bitstyle.sbab.util;

import java.util.UUID;

/**
 * Interface for providing string based Ids.
 *
 * @author cspiking
 */
public interface IdProvider {

    String provideId();

    final class UUIDProvider implements IdProvider {
        @Override
        public String provideId() {
            return UUID.randomUUID().toString();
        }
    }

}
