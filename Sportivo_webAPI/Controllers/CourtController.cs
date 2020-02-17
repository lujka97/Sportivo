using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Primitives;
using Sportivo_webAPI.Models;
using Sportivo_webAPI.Repositories;
using System;
using System.Collections.Generic;
using System.IdentityModel.Tokens.Jwt;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CourtController : Controller
    {
        private readonly CourtRepository _courtRepository;
        public CourtController()
        {
            _courtRepository = new CourtRepository();
        }
        [HttpGet]
        [Route("getCourts")]
        public IActionResult Get(int companyId, int sportId)
        {
            return Ok(_courtRepository.GetAllOfCompanyForSport(companyId, sportId));
        }

        [HttpPost]
        [Route("add")]
        [Authorize]
        public IActionResult Add([FromBody]Court court)
        {
            StringValues headerValues;

            if (Request.Headers.TryGetValue("Authorization", out headerValues))
            {
                string token_str = headerValues.First();
                string[] token_arr = token_str.Split(" ");
                var handler = new JwtSecurityTokenHandler();
                var token = handler.ReadJwtToken(token_arr[1]);

                var user = token.Payload["unique_name"];

                int userId = int.Parse(user.ToString());

                return Ok(_courtRepository.Add(court, userId));
            }

            return BadRequest();
        }
    }
}
