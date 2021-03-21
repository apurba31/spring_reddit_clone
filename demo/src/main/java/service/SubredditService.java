package service;

import org.springframework.transaction.annotation.Transactional;

import antlr.collections.List;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import dto.SubredditDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mapper.SubredditMapper;
import model.Subreddit;
import repository.SubredditRepository;

@Service
@AllArgsConstructor
@Slf4j
public class SubredditService 
{
	@Transactional
	private SubredditRepository subredditRepository;
	private SubredditMapper subredditMapper;
	
	public SubredditDto save(SubredditDto subRedditDto)
	{
		 Subreddit save = subredditRepository.save(mapSubredditDto(subRedditDto));
		 subRedditDto.setId(save.getId());
		 return subRedditDto;
	}
	
	private void mapSubredditDto(SubredditDto subRedditDto)
	{
		Subreddit.builder().name(subRedditDto.getName())
				.description(subRedditDto.getDescription())
				.build();
	}
	
	@Transactional(readOnly = true)
	public List<SubredditDto> getall() {
		return subredditRepository.findAll()
							.stream()
							.map(this::mapToDto)
							.collect(toList());
	}
	
	private SubredditDto mapToDto(Subreddit subReddit)
	{
		return null;
	}
}
