package bg.softuni.bookworld.service.exeption;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public class ObjectNotFoundException extends RuntimeException {

        private final String objectType;
        private final String identifier;

        public ObjectNotFoundException(String objectType, String identifier) {
            super(objectType + " not found with identifier: " + identifier);
            this.objectType = objectType;
            this.identifier = identifier;
        }

        public String getObjectType() {
            return objectType;
        }

        public String getIdentifier() {
            return identifier;
        }
}
