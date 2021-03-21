package mapper;

import org.mapstruct.Mapper;

import dto.SubredditDto;
import model.Subreddit;

@Mapper(component = "spring")
public interface SubredditMapper 
{
	@Mapping(target = "numberofPosts", expression = "java(mapPosts(subreddit.getPosts()))")
	 SubredditDto mapSubredditToDto(Subreddit subreddit);
		default Integer mapPosts(List<Post> numberOfPosts)
		{
			return numberOfPosts.size();
		}
		
		@InheritInverseConfiguration
	    @Mapping(target = "posts", ignore = true)
	    Subreddit mapDtoToSubreddit(SubredditDto subredditDto);
}
