package es.upm.etsisi.fis.fisfleet.infrastructure.services;

public interface CrudService<RQ, EN, ID> {

    EN create(RQ request);

    EN read(ID id);

    EN update(RQ request, ID id);

    void delete(ID id);

}
