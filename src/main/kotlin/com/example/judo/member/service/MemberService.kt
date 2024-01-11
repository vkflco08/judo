package com.example.judo.member.service

import com.example.judo.common.exception.InvalidInputException
import com.example.judo.member.dto.MemberDtoRequest
import com.example.judo.member.entity.Member
import com.example.judo.member.repository.MemberRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Transactional
@Service
class MemberService(
    private val memberRepository: MemberRepository
) {
    fun signUp(memberDtoRequest: MemberDtoRequest): String {
        var member = memberRepository.findByLoginId(memberDtoRequest.loginId)
        if (member != null) {
            throw InvalidInputException("loginId", "이미 등록된 ID 입니다.")
        }
        member = memberDtoRequest.toEntity()
        memberRepository.save(member)

        return "회원가입이 완료되었습니다."
    }
}