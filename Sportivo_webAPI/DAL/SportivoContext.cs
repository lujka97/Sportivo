using Microsoft.EntityFrameworkCore;
using Sportivo_webAPI.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace Sportivo_webAPI.DAL
{
    public class SportivoContext : DbContext
    {
        public SportivoContext(DbContextOptions<SportivoContext> options) : base(options)
        {

        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            string cn = @"Server=(localdb)\mssqllocaldb;Database=SportivoDB;Trusted_Connection=True;ConnectRetryCount=0";
            optionsBuilder.UseSqlServer(cn);

            base.OnConfiguring(optionsBuilder);
        }

        public DbSet<Company> Companies { get; set; }
        public DbSet<Court> Courts { get; set; }
        public DbSet<User> Users { get; set; }
        public DbSet<Reservation> Reservations { get; set; }
        public DbSet<Sport> Sports { get; set; }
    }
}
