package org.machinestalk.mapper;

import org.machinestalk.api.dto.UserDto;
import org.machinestalk.domain.User;


/**
 * I prefer using mapstruct mappers instead of ModelMapper, but for the purpose of this test I will create
 * a manual mapper as utility method
 */
public abstract class UserMapper {
    private UserMapper() {
    }

    public static UserDto fromUserToUserDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(String.valueOf(user.getId()));
        UserDto.UserInfos infos = new UserDto.UserInfos(
                user.getFirstName(),
                user.getLastName(),
                user.getDepartmentId() != null ? user.getDepartmentId().toString() : null,
                null); // fix me : map addresses
        dto.setUserInfos(infos);
        return dto;
    }
}
