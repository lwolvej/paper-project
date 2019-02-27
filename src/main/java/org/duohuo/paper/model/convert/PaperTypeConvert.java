package org.duohuo.paper.model.convert;

import org.duohuo.paper.model.PaperType;

import javax.persistence.AttributeConverter;

public class PaperTypeConvert implements AttributeConverter<PaperType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PaperType type) {
        return type.getValue();
    }

    @Override
    public PaperType convertToEntityAttribute(Integer integer) {
        return PaperType.fromValue(integer);
    }
}
