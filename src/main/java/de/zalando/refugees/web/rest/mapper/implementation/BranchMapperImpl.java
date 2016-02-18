package de.zalando.refugees.web.rest.mapper.implementation;

import de.zalando.refugees.domain.*;
import de.zalando.refugees.repository.OrganizationRepository;
import de.zalando.refugees.service.GeoCodingService;
import de.zalando.refugees.web.rest.dto.BranchDTO;
import de.zalando.refugees.web.rest.mapper.BranchMapper;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.maps.model.LatLng;

/**
 * Mapper for the entity Branch and its DTO BranchDTO.
 */
public class BranchMapperImpl implements BranchMapper
{

	@Autowired
	GeoCodingService geoCodingService;

	@Inject
	private OrganizationRepository organizationRepository;

	@Override
	public BranchDTO branchToBranchDTO( Branch branch )
	{
		BranchDTO dto = new BranchDTO();

		dto.setAddress( branch.getAddress() );
		dto.setEmail( branch.getEmail() );
		dto.setId( branch.getId() );
		dto.setPhone( branch.getPhone() );
		dto.setLng( branch.getLng() );
		dto.setLat( branch.getLat() );

		if ( branch.getOrganization() != null )
		{
			dto.setOrganizationId( branch.getOrganization().getId() );
			dto.setOrganizationName( branch.getOrganization().getName() );
		}

		return dto;
	}

	@Override
	public Branch branchDTOToBranch( BranchDTO branchDTO )
	{
		Branch branch = new Branch();
		Organization org = null;

		if ( branchDTO.getOrganizationId() != null )
		{
			org = organizationRepository.findOne( branchDTO.getOrganizationId() );
		}

		branch.setAddress( branchDTO.getAddress() );
		branch.setEmail( branchDTO.getEmail() );
		branch.setId( branchDTO.getId() );
		branch.setPhone( branchDTO.getPhone() );
		branch.setOrganization( org );

		LatLng geoPoint = geoCodingService.getGeoPoint( branch.getAddress() );
		branch.setLng( geoPoint.lng );
		branch.setLat( geoPoint.lat );

		return branch;
	}
}
