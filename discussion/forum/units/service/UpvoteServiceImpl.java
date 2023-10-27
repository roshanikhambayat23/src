//package discussion.forum.units.service;
//
//import java.util.ArrayList;
//import java.util.Objects;
//
//import com.forum.units.Question;
//import com.forum.units.Reply;
//import com.forum.units.Upvote;
//import com.forum.units.User;
//
//public class UpvoteServiceImpl implements UpvoteService {
//	public static ArrayList<Upvote> upvotes = new ArrayList<>();
//
//	public Upvote addUpvote(Question question, User user) {
//		if (question != null && user != null) {
//			Upvote upvote = getUpvote(user, question, null);
//			if (upvote != null) {
//				System.out.println("You have already upvoted");
//				return upvote;
//			}
//			upvote = addUpvote(user, question, null);
//			question.increaseUpvoteCount();
//			return upvote;
//		}
//		System.out.println("Any specified field can't be empty");
//		return null;
//	}
//
//	public long upvoteCount(Reply reply) {
//		int count = 0;
//		for (Upvote upvote : upvotes) {
//			if (upvote != null && (upvote.getReply() == reply)) {
//				count++;
//			}
//		}
//		return count;
//	}
//
//	public Upvote addUpvote(Reply reply, User user) {
//		if (reply != null && user != null) {
//			Upvote upvote = getUpvote(user, null, reply);
//			if (upvote != null) {
//				System.out.println("You have already upvoted");
//				return upvote;
//			}
//			upvote = addUpvote(user, null, reply);
//			return upvote;
//		}
//		System.out.println("Any specified field can't be empty");
//		return null;
//	}
//
//	private Upvote getUpvote(User user, Question question, Reply reply) {
//		for (Upvote upvote : upvotes) {
//
//			/*
//			  Change the below if condition such that user can not upvote the same question or same reply twice,
//			  but can upvote multiple questions and replies
//			*/
////
//			if (reply==null && upvote.getUser() == user && upvote.getQuestion() == question ) {
//				return upvote;
//			}
//				else if ( question==null && upvote.getUser() == user && upvote.getReply() == reply) {
//					return upvote;
//			}
//
//		}
//		return null;
//	}
//
//	private Upvote addUpvote(User user, Question question, Reply reply) {
//		Upvote upvote = new Upvote();
//		upvote.setQuestion(question);
//		upvote.setReply(reply);
//		upvote.setUser(user);
//		upvote.autoGenerateId();
//		upvote.setCreated();
//		upvotes.add(upvote);
//		return upvote;
//	}
//}
package discussion.forum.units.service;

import com.forum.units.Question;
import com.forum.units.Reply;
import com.forum.units.Upvote;
import com.forum.units.User;
import com.forum.units.UserRole;
import java.util.ArrayList;
import java.util.Iterator;

public class UpvoteServiceImpl implements UpvoteService {
	public static ArrayList<Upvote> upvotes = new ArrayList();

	public UpvoteServiceImpl() {
	}

	public Upvote addUpvote(Question question, User user) {
		if (question != null && user != null) {
			Upvote upvote = this.getUpvote(user, question, (Reply)null);
			if (upvote != null) {
				System.out.println("You have already upvoted");
				return upvote;
			} else if (user.getUserRole() != UserRole.ADMIN && user.getUserRole() != UserRole.MODERATOR && question.getUser().getUsername() == user.getUsername()) {
				System.out.println("You cannot upvote your own question");
				return upvote;
			} else {
				upvote = this.addUpvote(user, question, (Reply)null);
				question.increaseUpvoteCount();
				return upvote;
			}
		} else {
			System.out.println("Any specified field can't be empty");
			return null;
		}
	}

	public long upvoteCount(Reply reply) {
		int count = 0;
		Iterator var3 = upvotes.iterator();

		while(var3.hasNext()) {
			Upvote upvote = (Upvote)var3.next();
			if (upvote != null && upvote.getReply() == reply) {
				++count;
			}
		}

		return (long)count;
	}

	public Upvote addUpvote(Reply reply, User user) {
		if (reply != null && user != null) {
			Upvote upvote = this.getUpvote(user, (Question)null, reply);
			if (upvote != null) {
				System.out.println("You have already upvoted");
				return upvote;
			} else if (user.getUserRole() != UserRole.ADMIN && user.getUserRole() != UserRole.MODERATOR && reply.getUser().getUsername() == user.getUsername()) {
				System.out.println("You cannot upvote your own reply");
				return upvote;
			} else {
				upvote = this.addUpvote(user, (Question)null, reply);
				return upvote;
			}
		} else {
			System.out.println("Any specified field can't be empty");
			return null;
		}
	}

	private Upvote getUpvote(User user, Question question, Reply reply) {
		Iterator var4 = upvotes.iterator();

		Upvote upvote;
		do {
			if (!var4.hasNext()) {
				return null;
			}

			upvote = (Upvote)var4.next();
			if (reply == null && upvote.getUser() == user && upvote.getQuestion() == question) {
				return upvote;
			}
		} while(question != null || upvote.getUser() != user || upvote.getReply() != reply);

		return upvote;
	}

	private Upvote addUpvote(User user, Question question, Reply reply) {
		Upvote upvote = new Upvote();
		upvote.setQuestion(question);
		upvote.setReply(reply);
		upvote.setUser(user);
		upvote.autoGenerateId();
		upvote.setCreated();
		upvotes.add(upvote);
		return upvote;
	}
}