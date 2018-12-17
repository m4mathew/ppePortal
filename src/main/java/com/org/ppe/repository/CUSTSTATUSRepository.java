package com.org.ppe.repository;

import com.org.ppe.domain.CUSTSTATUS;
import com.org.ppe.service.dto.SUMMARYDTO;

import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CUSTSTATUS entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CUSTSTATUSRepository extends JpaRepository<CUSTSTATUS, Long> {
	
	@Query(value="SELECT DISTINCT * FROM (SELECT custstatus.*" + 
			" FROM (SELECT cust_id, MAX(change_dt) AS change_dt FROM custstatus" + 
			" GROUP BY cust_id) AS latest_rec" + 
			" INNER JOIN custstatus ON  custstatus.cust_id = latest_rec.cust_id AND custstatus.change_dt = latest_rec.change_dt)", 
			nativeQuery=true)
    public List<CUSTSTATUS> findOnlyLatest();
	

	@Query(value="SELECT SUBSTR(cust_id,2,2) AS unitId , ppe_status AS ppeStatus, COUNT(cust_id) AS nosCus, AVG(DATEDIFF(d,change_dt,SYSDATE())) AS avgDays FROM (SELECT custstatus.*" + 
			" FROM (SELECT cust_id, MAX(change_dt) AS change_dt FROM custstatus" + 
			" GROUP BY cust_id) AS latest_rec" + 
			" INNER JOIN custstatus ON  custstatus.cust_id = latest_rec.cust_id AND custstatus.change_dt = latest_rec.change_dt) "
			+ "GROUP BY SUBSTR(cust_id,2,2),ppe_status", 
			nativeQuery=true)
    public List<SUMMARYDTO> findSumary();
	
}
