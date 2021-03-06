package com.opencryptotrade.templatebuilder.repository;

import com.opencryptotrade.templatebuilder.entity.EmailTemplate;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailTemplateRepository extends CrudRepository<EmailTemplate, ObjectId> {

}
