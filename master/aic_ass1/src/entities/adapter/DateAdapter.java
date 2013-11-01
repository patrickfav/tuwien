package entities.adapter;

/**
 * @author Favre-Bulle, Rauscha
 * Advanced Internet Computing
 * Assignment 1
 */

import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlAdapter;


public class DateAdapter extends XmlAdapter<Long,Date> {

    @Override
    public Date unmarshal(Long v) throws Exception {
        return new Date(v);
    }

    @Override
    public Long marshal(Date v) throws Exception {
        return v.getTime();
    }
    
}
