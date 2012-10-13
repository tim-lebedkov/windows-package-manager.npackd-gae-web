package com.googlecode.npackdweb;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;

/**
 * Save or create a package.
 */
public class PackageSaveAction extends Action {
	/**
	 * -
	 */
	public PackageSaveAction() {
		super("^/p/save$", ActionSecurityType.ADMINISTRATOR);
	}

	@Override
	public Page perform(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String id = req.getParameter("id");
		Objectify ofy = ObjectifyService.begin();
		Package p;
		if (id == null || id.trim().length() == 0) {
			p = new Package();
			p.name = req.getParameter("name");
			p.description = req.getParameter("description");
			p.icon = req.getParameter("icon");
			p.title = req.getParameter("title");
			p.url = req.getParameter("url");
			p.icon = req.getParameter("icon");
		} else {
			long id_ = Long.parseLong(id);
			p = ofy.get(new Key<Package>(Package.class, id_));
			if (p == null)
				throw new IOException("Package does not exist");
		}
		ofy.put(p);
		resp.sendRedirect("/p");
		return null;
	}
}
