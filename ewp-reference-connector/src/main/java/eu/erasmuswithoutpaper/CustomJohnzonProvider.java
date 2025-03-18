package eu.erasmuswithoutpaper;

import eu.erasmuswithoutpaper.omobility.las.control.ByteAdapter;
import eu.erasmuswithoutpaper.omobility.las.control.ByteConverter;
import org.apache.johnzon.mapper.Mapper;
import org.apache.johnzon.mapper.MapperBuilder;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;


@Provider
public class CustomJohnzonProvider implements ContextResolver<Mapper> {
    private final Mapper mapper;

    public CustomJohnzonProvider() {
        this.mapper = new MapperBuilder()
                .addAdapter(new ByteAdapter())
                .addConverter(Byte.class, new ByteConverter())
                .build();
    }

    @Override
    public Mapper getContext(Class<?> type) {
        return mapper;
    }
}
