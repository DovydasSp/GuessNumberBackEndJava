package eu.openg.guessnumberapi.rest.entity.converter;

public interface RestResponseConverter<I, O> {
    O convert(I input);
}
