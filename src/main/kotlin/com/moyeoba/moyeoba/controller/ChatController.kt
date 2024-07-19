package com.moyeoba.moyeoba.controller

import com.moyeoba.moyeoba.data.dto.request.CreateChatRoomRequestDto
import com.moyeoba.moyeoba.data.dto.request.ExitRoomRequestDto
import com.moyeoba.moyeoba.data.dto.request.InviteRequestDto
import com.moyeoba.moyeoba.data.dto.request.RegisterEmailDto
import com.moyeoba.moyeoba.data.dto.response.GetChatRoomsResponseDto
import com.moyeoba.moyeoba.data.dto.response.GetMessagesResponse
import com.moyeoba.moyeoba.security.UserDetailsImpl
import com.moyeoba.moyeoba.service.ChatRoomService
import com.moyeoba.moyeoba.service.ChatService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/chat")
class ChatController {
    @Autowired
    private lateinit var chatService: ChatService
    @Autowired
    private lateinit var chatRoomService: ChatRoomService

    @PostMapping("/create/room")
    fun createChatRoom(@RequestBody requestDto: CreateChatRoomRequestDto,
                       @AuthenticationPrincipal userDetails: UserDetailsImpl
    ): ResponseEntity<Boolean> {
        //TODO: 바꾸기 -> user id를 직접적으로 이용하는건 보안 상 좋지 않음.
        if(requestDto.userId != userDetails.user.id) {
            return ResponseEntity.badRequest().build()
        }

        return ResponseEntity.ok(chatRoomService.createChatRoom(
            requestDto.roomName,
            requestDto.userList.apply { add(requestDto.userId) }
        ))
    }

    @PostMapping("/invite")
    fun inviteUsers(@RequestBody requestDto: InviteRequestDto): ResponseEntity<Boolean> {
        return ResponseEntity.ok(chatRoomService.invite(requestDto.roomId, requestDto.userIdList))
    }
    @PostMapping("/exit")
    fun exitRoom(@RequestBody requestDto: ExitRoomRequestDto): ResponseEntity<Boolean> {
        return ResponseEntity.ok(chatRoomService.exit(requestDto.roomId, requestDto.userId))
    }

    @GetMapping("/get/rooms")
    fun getRooms(@AuthenticationPrincipal userDetails: UserDetailsImpl): ResponseEntity<GetChatRoomsResponseDto> {
        return ResponseEntity.ok(chatRoomService.getRooms(userDetails.user.id!!))
    }

    @GetMapping("/get/recent_messages")
    fun getMessages(
        @RequestParam(value = "roomId") roomId: Long,
        @RequestParam(value = "page") page: Int
    ): ResponseEntity<GetMessagesResponse> {
        return ResponseEntity.ok(chatService.getMessages(roomId, page))
    }


}