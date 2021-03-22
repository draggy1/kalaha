package com.kalaha.services.dto;

import com.kalaha.domain.Player;
import java.net.URI;
import java.util.Map;
import lombok.Value;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Value(staticConstructor = "of")
public class AfterMove {
	long id;
	URI url;
	Map<Integer, Integer> status;
	Player player;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;

		if (o == null || getClass() != o.getClass()) return false;

		AfterMove afterMove = (AfterMove) o;

		return new EqualsBuilder()
				.append(id, afterMove.id)
				.append(url, afterMove.url)
				.append(status, afterMove.status)
				.append(player, afterMove.player)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append(id)
				.append(url)
				.append(status)
				.append(player)
				.toHashCode();
	}
}
