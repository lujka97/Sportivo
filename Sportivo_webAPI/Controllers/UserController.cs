using Microsoft.AspNetCore.Mvc;
using Sportivo_webAPI.Models;
using Sportivo_webAPI.Repositories;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApplication1;

namespace Sportivo_webAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : Controller
    {
        private readonly UserRepository _userRepository;
        public UserController()
        {
            _userRepository = new UserRepository();
        }

        [HttpPost]
        [Route("register")]
        public IActionResult Register ([FromBody]User User)
        {
            if (_userRepository.GetByUserName(User.Username) != null)
                return StatusCode(409);

            User.Password = PasswordHelper.ComputeSha256Hash(User.Password);

            if (_userRepository.Add(User))
                return Ok();
            return StatusCode(500);
        }

        [HttpPost]
        [Route("login")]
        public IActionResult Login ([FromBody]User user)
        {
            user.Password = PasswordHelper.ComputeSha256Hash(user.Password);
            return Ok(_userRepository.Authenticate(user.Username, user.Password));
        }

        [HttpGet]
        [Route("checkusername")]
        public IActionResult CheckUsernameAvailable(string username)
        {
            if (_userRepository.GetByUserName(username) == null)
                return Ok(true);
            return Ok(false);
        }
    }
}
