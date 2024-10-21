package com.sporta.member.service;

import com.sporta.member.dto.MemberDTO;
import com.sporta.member.model.Member;
import com.sporta.member.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
    The service layer contains the business logic for handling Member objects.
    This class interacts with the MemberRepository and converts between entities and DTOs.
 */

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<MemberDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<MemberDTO> getMemberById(Long id) {
        return memberRepository.findById(id)
                .map(this::convertToDTO);
    }

    public MemberDTO createMember(MemberDTO memberDTO) {
        Member member = convertToEntity(memberDTO);
        Member savedMember = memberRepository.save(member);
        return convertToDTO(savedMember);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    // Utility Methods for Conversion
    private MemberDTO convertToDTO(Member member) {
        return new MemberDTO(member.getId(), member.getName(), member.getEmail(), member.getPhoneNumber());
    }

    private Member convertToEntity(MemberDTO memberDTO) {
        return new Member(memberDTO.getId(), memberDTO.getName(), memberDTO.getEmail(), memberDTO.getPhoneNumber());
    }
}
