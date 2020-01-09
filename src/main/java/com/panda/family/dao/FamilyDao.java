package com.panda.family.dao;

import com.panda.family.domain.Family;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FamilyDao {

    long insertFamily(Family family);

    void updateFamily(Family family);

    void removeFamily(long familyId);

    Family queryFamilyById(@Param("id") long id);

    Family queryFamilyByCreatorIdAndFamilyName(@Param("creatorId") long creatorId, @Param("familyName") String familyName);

    List<Family> queryFamilyByCreatorId(@Param("creatorId") long creatorId);
}
