package cn.mahjong.beans.common;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class ModelMappers {
    
    private ModelMappers(){}

    private static ThreadLocal<ModelMapper> strictMapper = new ThreadLocal<ModelMapper>() {
        @Override
        protected ModelMapper initialValue() {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            return mapper;
        }
    };
    
    private static ThreadLocal<ModelMapper> standardMapper = new ThreadLocal<ModelMapper>() {
        @Override
        protected ModelMapper initialValue() {
            return new ModelMapper();
        }
    };

    public static <D> D from(Object source, Class<D> clazz) {
        return strictMapper.get().map(source, clazz);
    }
    
    public static <D> D to(Object source, Class<D> clazz){
        return standardMapper.get().map(source, clazz);
    }
    
    public static <D> List<D> toList(List<?> list, Class<D> clazz){
        return list.stream().map(from -> to(from, clazz)).collect(Collectors.toList());
    }
}
