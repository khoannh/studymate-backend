package exe201.studymatebackend.service.impl;

import exe201.studymatebackend.dto.request.room.CreateRoomRequest;
import exe201.studymatebackend.dto.response.room.*;
import exe201.studymatebackend.enums.RoomRole;
import exe201.studymatebackend.exception.AppException;
import exe201.studymatebackend.exception.ErrorCode;
import exe201.studymatebackend.pojo.Account;
import exe201.studymatebackend.pojo.AccountRoom;
import exe201.studymatebackend.pojo.Room;
import exe201.studymatebackend.pojo.Topic;
import exe201.studymatebackend.repository.*;
import exe201.studymatebackend.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountRoomRepository accountRoomRepository;

    @Autowired
    private ActionRepository actionRepository;
    @Autowired
    private TopicRepository topicRepository;

    @Transactional
    @Override
    public CreateRoomResponse createRoom(CreateRoomRequest request) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer accountID = account.getAccountID();
        Account owner = accountRepository.findByAccountID(accountID);
        int coin = owner.getCoin();
        if (actionRepository.findByActionName("Create new room") == null) {
            throw new AppException(ErrorCode.ACTION_DOES_NOT_EXIST);
        }
        int cost = actionRepository.findByActionName("Create new room").getActionCoin();
        if (coin < cost) {
            throw new AppException(ErrorCode.NOT_ENOUGH_COIN);
        } else {
            coin = coin - cost;
        }
        owner.setCoin(coin);

        if (roomRepository.findByRoomName(request.getRoomName()) != null) {
            throw new AppException(ErrorCode.ROOM_ALREADY_EXIST);
        }

        Room room = new Room();
        room.setRoomName(request.getRoomName());
        room.setRoomDescription(request.getRoomDescription());
        room.setPublic(request.isPublic());
        Topic topic = topicRepository.findAll().stream()
                .filter(t -> t.getTopicName().equalsIgnoreCase(request.getTopic()))
                .findFirst()
                .orElseThrow(() -> new AppException(ErrorCode.TOPIC_DOES_NOT_EXIST));
        room.setTopic(topic);
        room.setCreatedAt(LocalDateTime.now());
        room.setActive(true);
        room.setMaxNumberOfMembers(request.getMaxNumberOfMembers());
        room.setNumberOfMembers(1);
        roomRepository.save(room);

        AccountRoom accountRoom = new AccountRoom();
        accountRoom.setAccount(owner);
        accountRoom.setRoom(room);
        accountRoom.setRoomRole(RoomRole.OWNER);
        accountRoom.setJoinedAt(LocalDateTime.now());
        accountRoomRepository.save(accountRoom);

        if (owner.getAccountRoomList() == null) {
            owner.setAccountRoomList(new ArrayList<>());
        }
        owner.getAccountRoomList().add(accountRoom);

        if (room.getAccountRoomList() == null) {
            room.setAccountRoomList(new ArrayList<>());
        }
        room.getAccountRoomList().add(accountRoom);

        return CreateRoomResponse.builder()
                .roomID(room.getRoomID())
                .roomName(room.getRoomName())
                .roomDescription(room.getRoomDescription())
                .topic(room.getTopic().getTopicName())
                .createdAt(room.getCreatedAt())
                .isActive(room.isActive())
                .maxNumberOfMembers(room.getMaxNumberOfMembers())
                .isPublic(room.isPublic())
                .build();
    }

    @Override
    @Transactional
    public void joinRoom(Integer roomID) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer accountID = account.getAccountID();
        Account member = accountRepository.findByAccountID(accountID);
        Room room = roomRepository.findByRoomID(roomID);
        if (!room.isActive()) {
            throw new AppException(ErrorCode.ROOM_IS_INACTIVE);
        }
        if (room.getNumberOfMembers() >= room.getMaxNumberOfMembers()) {
            throw new AppException(ErrorCode.ROOM_ALREADY_FULL);
        }
        // join lại sau khi đã rời
        Optional<AccountRoom> existingAccountRoomOpt = accountRoomRepository.findByAccountAndRoom(member, room);
        if (existingAccountRoomOpt.isPresent()) {
            AccountRoom existingAccountRoom = existingAccountRoomOpt.get();
            if (existingAccountRoom.getLeftAt() == null) {
                throw new AppException(ErrorCode.ACCOUNT_ALREADY_JOINED_ROOM);
            } else {
                existingAccountRoom.setLeftAt(null);
                existingAccountRoom.setJoinedAt(LocalDateTime.now());
                existingAccountRoom.setRoomRole(RoomRole.MEMBER);
                accountRoomRepository.save(existingAccountRoom);
                room.setNumberOfMembers(room.getNumberOfMembers() + 1);
                roomRepository.save(room);
                int coin = member.getCoin();
                if (actionRepository.findByActionName("Join room") == null) {
                    throw new AppException(ErrorCode.ACTION_DOES_NOT_EXIST);
                }
                int cost = actionRepository.findByActionName("Join room").getActionCoin();
                if (coin < cost) {
                    throw new AppException(ErrorCode.NOT_ENOUGH_COIN);
                } else {
                    coin = coin - cost;
                }
                member.setCoin(coin);
                return;
            }

        }
        // join lần đầu tiên
        AccountRoom accountRoom = new AccountRoom();
        accountRoom.setAccount(member);
        accountRoom.setRoom(room);
        accountRoom.setRoomRole(RoomRole.MEMBER);
        accountRoom.setJoinedAt(LocalDateTime.now());
        accountRoomRepository.save(accountRoom);

        if (member.getAccountRoomList() == null) {
            member.setAccountRoomList(new ArrayList<>());
        }
        member.getAccountRoomList().add(accountRoom);
        int coin = member.getCoin();
        if (actionRepository.findByActionName("Join room") == null) {
            throw new AppException(ErrorCode.ACTION_DOES_NOT_EXIST);
        }
        int cost = actionRepository.findByActionName("Join room").getActionCoin();
        if (coin < cost) {
            throw new AppException(ErrorCode.NOT_ENOUGH_COIN);
        } else {
            coin = coin - cost;
        }
        member.setCoin(coin);

        if (room.getAccountRoomList() == null) {
            room.setAccountRoomList(new ArrayList<>());
        }
        room.getAccountRoomList().add(accountRoom);
        room.setNumberOfMembers(room.getNumberOfMembers() + 1);
        roomRepository.save(room);
    }

    @Override
    public GetRoomPageResponse getAllRoom(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Room> rooms = roomRepository.findAll(pageable);
        List<GetAllRoomResponse> content = rooms.getContent().stream().map(room -> GetAllRoomResponse.builder()
                .roomID(room.getRoomID())
                .roomName(room.getRoomName())
                .roomDescription(room.getRoomDescription())
                .topic(room.getTopic().getTopicName())
                .createdAt(room.getCreatedAt())
//                .ownerName(accountRepository.findByAccountID(accountRoomRepository.findAccountIdByRoomAndRoomRole(room, RoomRole.OWNER)).getUsername())
                .isActive(room.isActive())
                .maxNumberOfMembers(room.getMaxNumberOfMembers())
                .isPublic(room.isPublic())
                .numberOfMembers(room.getNumberOfMembers())
                .build()).toList();
        return GetRoomPageResponse.builder()
                .content(content)
                .pageNumber(rooms.getNumber())
                .pageSize(rooms.getSize())
                .totalElements(rooms.getTotalElements())
                .totalPages(rooms.getTotalPages())
                .isLastPage(rooms.isLast())
                .build();
    }

    @Override
    public GetRoomInfoResponse getRoomInfo(int roomID) {
        Room room = roomRepository.findByRoomID(roomID);
        if (room == null) {
            throw new AppException(ErrorCode.ROOM_DOES_NOT_EXIST);
        }
        return GetRoomInfoResponse.builder()
                .roomID(room.getRoomID())
                .roomName(room.getRoomName())
                .roomDescription(room.getRoomDescription())
                .topic(room.getTopic().getTopicName())
                .createdAt(room.getCreatedAt())
                .isActive(room.isActive())
                .maxNumberOfMembers(room.getMaxNumberOfMembers())
                .isPublic(room.isPublic())
                .numberOfMembers(room.getNumberOfMembers())
                .build();
    }

    @Override
    public GetMyRoomResponse getMyRoom(int page, int size) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "room.createdAt"));
        Page<AccountRoom> accountRooms = accountRoomRepository.findAllByAccountAndLeftAtIsNull(account, pageable);
        List<GetMyRoomResponse.RoomInfo> content = accountRooms.getContent().stream().map(accountRoom -> {
            Room room = accountRoom.getRoom();
            return GetMyRoomResponse.RoomInfo.builder()
                    .roomID(room.getRoomID())
                    .roomName(room.getRoomName())
                    .build();
        }).toList();
        return GetMyRoomResponse.builder()
                .content(content)
                .pageNumber(accountRooms.getNumber())
                .pageSize(accountRooms.getSize())
                .totalElements(accountRooms.getTotalElements())
                .totalPages(accountRooms.getTotalPages())
                .isLastPage(accountRooms.isLast())
                .build();
    }

    @Override
    @Transactional
    public void leaveRoom(Integer roomID) {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer accountID = account.getAccountID();
        Room room = roomRepository.findByRoomID(roomID);
        if (room == null) {
            throw new AppException(ErrorCode.ROOM_DOES_NOT_EXIST);
        }
        Account member = accountRepository.findByAccountID(accountID);
        AccountRoom accountRoom = accountRoomRepository.findAccountRoomByAccountAndRoomAndLeftAtIsNull(member, room);
        if (accountRoom == null) {
            throw new AppException(ErrorCode.ACCOUNT_NOT_IN_ROOM);
        }
        if (accountRoom.getRoomRole() == RoomRole.OWNER) {
            if (room.getNumberOfMembers() == 1) {
                accountRoom.setLeftAt(LocalDateTime.now());
                accountRoomRepository.save(accountRoom);
                room.setActive(false);
                room.setNumberOfMembers(0);
                roomRepository.save(room);
                return;
            }
            throw new AppException(ErrorCode.OWNER_CANNOT_LEAVE_ROOM);
        }
        accountRoom.setLeftAt(LocalDateTime.now());
        accountRoomRepository.save(accountRoom);
        room.setNumberOfMembers(room.getNumberOfMembers() - 1);
        if (room.getNumberOfMembers() == 0) {
            room.setActive(false);
        }
        roomRepository.save(room);
    }
}
